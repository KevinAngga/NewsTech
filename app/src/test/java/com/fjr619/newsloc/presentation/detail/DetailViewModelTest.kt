package com.fjr619.newsloc.presentation.detail

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.fjr619.newsloc.MainCoroutineExtension
import com.fjr619.newsloc.data.local.NewsDaoFake
import com.fjr619.newsloc.data.repository.NewsRepositoryImplFake
import com.fjr619.newsloc.domain.model.Article
import com.fjr619.newsloc.domain.model.Source
import com.fjr619.newsloc.domain.usecase.news.DeleteArticle
import com.fjr619.newsloc.domain.usecase.news.GetArticle
import com.fjr619.newsloc.domain.usecase.news.GetBookmarks
import com.fjr619.newsloc.domain.usecase.news.GetNews
import com.fjr619.newsloc.domain.usecase.news.NewsUseCases
import com.fjr619.newsloc.domain.usecase.news.SearchNews
import com.fjr619.newsloc.domain.usecase.news.UpsertArticle
import com.fjr619.newsloc.presentation.bookmark.BookmarkViewModel
import com.fjr619.newsloc.presentation.common.NewsSnackbarVisual
import com.fjr619.newsloc.util.snackbar.SnackbarMessage
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
class DetailViewModelTest {
  private lateinit var newsUseCases: NewsUseCases
  private lateinit var viewModelTest: DetailViewModel
  private lateinit var getNews: GetNews
  private lateinit var searchNews: SearchNews
  private lateinit var upsertArticle: UpsertArticle
  private lateinit var deleteArticle: DeleteArticle
  private lateinit var getBookmarks: GetBookmarks
  private lateinit var getArticle: GetArticle
  private lateinit var repository: NewsRepositoryImplFake

  private lateinit var newsDaoFake: NewsDaoFake

  @BeforeEach
  fun setUp() {
    repository = NewsRepositoryImplFake()
    newsDaoFake = NewsDaoFake()
    getNews = mockk()
    searchNews = mockk()
    upsertArticle = UpsertArticle(newsDaoFake)
    deleteArticle = DeleteArticle(newsDaoFake)
    getBookmarks = GetBookmarks(repository)
    getArticle = GetArticle(newsDaoFake)

    newsUseCases = NewsUseCases(
      getNews,
      searchNews,
      upsertArticle,
      deleteArticle,
      getBookmarks,
      getArticle
    )

    viewModelTest = DetailViewModel(newsUseCases)
  }


  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `Test GetDetailBookmark Event`() = runTest {
    val article =   Article(
      author = "author",
      content = "content",
      description = "description",
      publishedAt = "publishAt",
      source = Source(
        id = "1",
        name = "name"
      ),
      title = "title1",
      url = "url",
      urlToImage = "urlToImage"
    )

//    coEvery { getArticle.invoke(any()) } returns article
    viewModelTest.onEvent(DetailEvent.GetDetailArticle(article))
    advanceUntilIdle()
    assertThat(viewModelTest.viewState.value.article).isEqualTo(article)
    assertThat(viewModelTest.viewState.value.snackbarMessage).isNull()
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `Test UpsertDeleteArticle on delete Event`() = runTest {
    val article =   Article(
      author = "author",
      content = "content",
      description = "description",
      publishedAt = "publishAt",
      source = Source(
        id = "1",
        name = "name"
      ),
      title = "title1",
      url = "url",
      urlToImage = "urlToImage"
    )

//    coEvery { getArticle.invoke(any()) } returns article
    viewModelTest.onEvent(DetailEvent.UpsertDeleteArticle(article))
    viewModelTest.onEvent(DetailEvent.GetDetailArticle(article))
    viewModelTest.onEvent(DetailEvent.UpsertDeleteArticle(article))
//    coEvery { deleteArticle.invoke(any()) } returns Unit
    advanceUntilIdle()
    assertThat(viewModelTest.viewState.value.bookmark).isNull()
  }


  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `Test UpsertDeleteArticle on upsert Event`() = runTest {
    val article =   Article(
      author = "author",
      content = "content",
      description = "description",
      publishedAt = "publishAt",
      source = Source(
        id = "1",
        name = "name"
      ),
      title = "title1",
      url = "url",
      urlToImage = "urlToImage"
    )

//    getArticle.invoke("asd")
    viewModelTest.onEvent(DetailEvent.UpsertDeleteArticle(article))
//    newsUseCases.upsertArticle.invoke(article)
    advanceUntilIdle()
    assertThat(viewModelTest.viewState.value.bookmark).isEqualTo(article)
    assertThat(viewModelTest.viewState.value.bookmark?.title).isEqualTo("title1")
  }

}