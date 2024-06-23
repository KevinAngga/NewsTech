package com.fjr619.newsloc.data.local

import androidx.paging.PagingSource
import com.fjr619.newsloc.domain.model.Article

class NewsDaoFake: NewsDao {
  private val articles : MutableList<Article> = mutableListOf()

  override suspend fun upsert(article: Article) {
    articles.add(article)
  }

  override suspend fun delete(article: Article) {
    articles.remove(article)
  }

  override fun getArticles(): PagingSource<Int, Article> {
    return ArticlePagingSourceFake(articles)
  }

  override suspend fun getArticle(url: String): Article? {
    return articles.find {
      it.url == url
    }
  }
}