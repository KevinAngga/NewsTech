package com.fjr619.newsloc.data.remote

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNull
import com.fjr619.newsloc.data.remote.dto.ErrorResponse
import com.fjr619.newsloc.data.remote.dto.NewsResponse
import com.fjr619.newsloc.domain.model.Article
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class NewsPagingSourceTest {

  private lateinit var api: NewsApi
  private lateinit var pagingSource: NewsPagingSource
  private lateinit var mockWebServer: MockWebServer

  @BeforeEach
  fun setUp() {
    mockWebServer = MockWebServer()
    api = Retrofit.Builder()
      .addConverterFactory(
        GsonConverterFactory
          .create()
      )
      .baseUrl(mockWebServer.url("/"))
      .build()
      .create()
    pagingSource = NewsPagingSource(api, "source")
  }


  @AfterEach
  fun tearDown() {
    mockWebServer.shutdown()
  }


  @Test
  fun `Test getRefreshKey when anchor is null`() {
    val pagingState = PagingState<Int, Article>(
      pages = listOf(),
      anchorPosition = null,
      config = PagingConfig(pageSize = 1),
      leadingPlaceholderCount = 0
    )

    val refreshKey = pagingSource.getRefreshKey(pagingState)
    assertThat(refreshKey).isNull()
  }


  @Test
  fun `Test getRefreshKey when anchor is valid and prevKey is available`() {
    val page = PagingSource.LoadResult.Page(
      data = articles,
      prevKey = 1,
      nextKey = 3
    )

    val pagingState = PagingState<Int, Article>(
      pages = listOf(page),
      anchorPosition = 1,
      config = PagingConfig(pageSize = 1),
      leadingPlaceholderCount = 0
    )

    val refreshKey = pagingSource.getRefreshKey(pagingState)
    assertThat(refreshKey).isEqualTo(2)
  }

  @Test
  fun `Test getRefreshKey when anchor is valid and next is available`() {
    val page = PagingSource.LoadResult.Page(
      data = articles,
      prevKey = null,
      nextKey = 2
    )

    val pagingState = PagingState<Int, Article>(
      pages = listOf(page),
      anchorPosition = 1,
      config = PagingConfig(pageSize = 1),
      leadingPlaceholderCount = 0
    )

    val refreshKey = pagingSource.getRefreshKey(pagingState)
    assertThat(refreshKey).isEqualTo(1)
  }

  @Test
  fun `Test Paging Source Success`() = runBlocking {

    val response = NewsResponse(
      articles = articles,
      status = "success",
      totalResults = articles.size
    )

    val mockResponse = MockResponse()
      .setResponseCode(200)
      .setBody(Gson().toJson(response))

    mockWebServer
      .enqueue(mockResponse)

    val expected = PagingSource.LoadResult.Page(
      data = response.articles,
      prevKey = null,
      nextKey = null
    )

    val resultPaging = pagingSource.load(
      PagingSource.LoadParams.Refresh(
        key = 0,
        loadSize = 1,
        placeholdersEnabled = false
      )
    )

    assertThat(response.articles.size).isEqualTo(3)
    assertThat(expected).isEqualTo(resultPaging)
  }


  @Test
  fun `Test Paging Source with return HttpError`() = runBlocking {
    val error = ErrorResponse(
      status = "error",
      code = "500",
      message = "Internal Server Error"
    )

    val mockResponse = MockResponse()
      .setResponseCode(500)
      .setBody(
        Gson().toJson(error)
      )

    mockWebServer.enqueue(mockResponse)

    val resultPaging = pagingSource.load(
      PagingSource.LoadParams.Refresh(
        key = 0,
        loadSize = 1,
        placeholdersEnabled = false
      )
    )

    assertThat(resultPaging).isInstanceOf(PagingSource.LoadResult.Error::class.java)
    val errorResult = resultPaging as PagingSource.LoadResult.Error
    assertThat(errorResult.throwable.message).isEqualTo("Internal Server Error")
  }


  @Test
  fun `Test Paging Source with Exception`() = runBlocking {
    val newsApi : NewsApi = mockk()
    pagingSource = NewsPagingSource(newsApi, "source")
    coEvery { newsApi.getNews(any(), any(), any()) } throws Exception("Network failure")

    val resultPaging = pagingSource.load(
      PagingSource.LoadParams.Refresh(
        key = 0,
        loadSize = 1,
        placeholdersEnabled = false
      )
    )

    assertThat(resultPaging).isInstanceOf(PagingSource.LoadResult.Error::class.java)
    val errorResult = resultPaging as PagingSource.LoadResult.Error
    assertThat(errorResult.throwable.message).isEqualTo("Network failure")
  }

}