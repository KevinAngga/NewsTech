package com.fjr619.newsloc.presentation.search

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.fjr619.newsloc.data.repository.NewsRepositoryImplFake
import com.fjr619.newsloc.domain.usecase.news.DeleteArticle
import com.fjr619.newsloc.domain.usecase.news.GetArticle
import com.fjr619.newsloc.domain.usecase.news.GetBookmarks
import com.fjr619.newsloc.domain.usecase.news.GetNews
import com.fjr619.newsloc.domain.usecase.news.NewsUseCases
import com.fjr619.newsloc.domain.usecase.news.SearchNews
import com.fjr619.newsloc.domain.usecase.news.UpsertArticle
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

  private lateinit var searchViewModel : SearchViewModel
  private lateinit var getNews: GetNews
  private lateinit var searchNews: SearchNews
  private lateinit var upsertArticle: UpsertArticle
  private lateinit var deleteArticle: DeleteArticle
  private lateinit var getBookmarks: GetBookmarks
  private lateinit var getArticle: GetArticle
  private lateinit var repository: NewsRepositoryImplFake
  private lateinit var newsUseCases: NewsUseCases

  @BeforeEach
  fun setUp() {
    repository = NewsRepositoryImplFake()
    getNews = mockk()
    searchNews = SearchNews(repository)
    upsertArticle = mockk()
    deleteArticle = mockk()
    getBookmarks = mockk()
    getArticle = mockk()

    newsUseCases = NewsUseCases(
      getNews,
      searchNews,
      upsertArticle,
      deleteArticle,
      getBookmarks,
      getArticle
    )

    searchViewModel = SearchViewModel(newsUseCases)
  }


  @Test
  fun `Test Query event update State`() = runTest{
    val searchQuery = "new query"
    searchViewModel.onEvent(SearchEvent.UpdateSearchQuery(searchQuery))
    assertThat(searchViewModel.state.value.searchQuery).isEqualTo(searchQuery)
  }
  
  
  
  @Test
  fun `Test search news got search articles`() = runTest() {
    searchViewModel.onEvent(SearchEvent.SearchNews)
    advanceUntilIdle()
    assertThat(searchViewModel.state.value.articles).isNotNull()
  }

}