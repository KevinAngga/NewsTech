package com.fjr619.newsloc.presentation.news_navigator

import assertk.assertThat
import assertk.assertions.isNotNull
import com.fjr619.newsloc.data.repository.NewsRepositoryImplFake
import com.fjr619.newsloc.domain.usecase.news.DeleteArticle
import com.fjr619.newsloc.domain.usecase.news.GetArticle
import com.fjr619.newsloc.domain.usecase.news.GetBookmarks
import com.fjr619.newsloc.domain.usecase.news.GetNews
import com.fjr619.newsloc.domain.usecase.news.NewsUseCases
import com.fjr619.newsloc.domain.usecase.news.SearchNews
import com.fjr619.newsloc.domain.usecase.news.UpsertArticle
import com.fjr619.newsloc.presentation.bookmark.BookmarkViewModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NewsNavigatorViewModelTest {
  private lateinit var newsUseCases: NewsUseCases
  private lateinit var viewModelTest: NewsNavigatorViewModel
  private lateinit var getNews: GetNews
  private lateinit var searchNews: SearchNews
  private lateinit var upsertArticle: UpsertArticle
  private lateinit var deleteArticle: DeleteArticle
  private lateinit var getBookmarks: GetBookmarks
  private lateinit var getArticle: GetArticle
  private lateinit var repository: NewsRepositoryImplFake

  @BeforeEach
  fun setUp() {
    repository = NewsRepositoryImplFake()
    getNews = mockk()
    searchNews = mockk()
    upsertArticle = mockk()
    deleteArticle = mockk()
    getBookmarks = GetBookmarks(repository)
    getArticle = mockk()

    newsUseCases = NewsUseCases(
      getNews,
      searchNews,
      upsertArticle,
      deleteArticle,
      getBookmarks,
      getArticle
    )

    newsUseCases.getBookmarks.invoke()
    viewModelTest = NewsNavigatorViewModel(newsUseCases)
  }


  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `Test get news success`() = runTest {
    advanceUntilIdle()
    assertThat(viewModelTest.state.value.articles).isNotNull()
  }

}