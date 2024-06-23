package com.fjr619.newsloc.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fjr619.newsloc.data.local.ArticlePagingSourceFake
import com.fjr619.newsloc.data.remote.NewsPagingSource
import com.fjr619.newsloc.data.remote.articles
import com.fjr619.newsloc.domain.model.Article
import com.fjr619.newsloc.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImplFake : NewsRepository {
  override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
    return Pager(
      config = PagingConfig(pageSize = 10),
      pagingSourceFactory = {
        ArticlePagingSourceFake(articles.toMutableList())
      }
    ).flow
  }

  override fun getBookmarks(): Flow<PagingData<Article>> {
    return Pager(
      config = PagingConfig(pageSize = 10),
      pagingSourceFactory = {
        ArticlePagingSourceFake(articles.toMutableList())
      }
    ).flow
  }

  override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
    return Pager(
      config = PagingConfig(pageSize = 10),
      pagingSourceFactory = {
        ArticlePagingSourceFake(articles.toMutableList())
      }
    ).flow
  }
}