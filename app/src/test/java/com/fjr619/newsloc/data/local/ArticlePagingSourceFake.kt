package com.fjr619.newsloc.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fjr619.newsloc.data.remote.articles
import com.fjr619.newsloc.domain.model.Article

class ArticlePagingSourceFake(
  val list: MutableList<Article>
) : PagingSource<Int, Article>() {
  override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
    return 0
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
    return LoadResult.Page(
      data = list,
      nextKey = null,
      prevKey = null
    )
  }
}