package com.fjr619.newsloc.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isSameAs
import assertk.assertions.isTrue
import com.fjr619.newsloc.data.local.ArticlePagingSourceFake
import com.fjr619.newsloc.data.local.NewsDao
import com.fjr619.newsloc.data.local.NewsDaoFake
import com.fjr619.newsloc.data.remote.NewsApi
import com.fjr619.newsloc.data.remote.NewsPagingSource
import com.fjr619.newsloc.data.remote.articles
import com.fjr619.newsloc.data.remote.dto.NewsResponse
import com.fjr619.newsloc.domain.model.Article
import com.fjr619.newsloc.domain.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NewsRepositoryImplTest {
  private lateinit var newsRepositoryImpl: NewsRepositoryImpl
  private lateinit var newsApi: NewsApi
  private lateinit var newsDao: NewsDao
  private lateinit var pagingSource: NewsPagingSource

  @BeforeEach
  fun setUp() {
    newsApi = mockk()
    newsDao = mockk()
    newsRepositoryImpl = NewsRepositoryImpl(newsApi, newsDao)
    pagingSource = NewsPagingSource(newsApi, "source")
  }
}