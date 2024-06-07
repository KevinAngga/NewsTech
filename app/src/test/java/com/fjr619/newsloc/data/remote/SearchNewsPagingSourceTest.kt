package com.fjr619.newsloc.data.remote

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNull
import assertk.assertions.isTrue
import com.fjr619.newsloc.data.remote.dto.NewsResponse
import com.fjr619.newsloc.domain.model.Article
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchNewsPagingSourceTest {

  private lateinit var api: NewsApi
  private lateinit var mockWebServer: MockWebServer
  private lateinit var searchNewsPagingSource: SearchNewsPagingSource

  @BeforeEach
  fun setUp() {
    mockWebServer = MockWebServer()
    api = mockk()
    searchNewsPagingSource = SearchNewsPagingSource(api, "searchQuery", "source")
  }


  @AfterEach
  fun tearDown() {
    mockWebServer.shutdown()
  }


  @Test
  fun `Checked if the keyReusedSupported are true`() {
    val result = searchNewsPagingSource.keyReuseSupported
    assertThat(result).isTrue()
  }


  @Test
  fun `Test getRefreshKey when anchor is null`() {
    val pagingState = PagingState<Int, Article>(
      pages = listOf(),
      anchorPosition = null,
      config = PagingConfig(pageSize = 1),
      leadingPlaceholderCount = 0
    )

    val refreshKey = searchNewsPagingSource.getRefreshKey(pagingState)
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

    val refreshKey = searchNewsPagingSource.getRefreshKey(pagingState)
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

    val refreshKey = searchNewsPagingSource.getRefreshKey(pagingState)
    assertThat(refreshKey).isEqualTo(1)
  }


  @Test
  fun `Test NewsPagingSource with Success Response`() = runBlocking {
    val response = NewsResponse(
      articles = articles,
      status = "success",
      totalResults = articles.size
    )

    coEvery { api.searchNews(any(), any(), any()) } returns response

    val expected = PagingSource.LoadResult.Page(
      data = response.articles,
      prevKey = null,
      nextKey = null
    )

    val resultPaging = searchNewsPagingSource.load(
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
  fun `Test NewsPagingSource with Exception`() = runBlocking {
    coEvery { api.searchNews(any(),any(), any()) } throws Exception("Network failure")

    val resultPaging = searchNewsPagingSource.load(
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