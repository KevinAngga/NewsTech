package com.fjr619.newsloc.presentation.news_navigator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.fjr619.newsloc.domain.usecase.news.NewsUseCases
import com.fjr619.newsloc.presentation.bookmark.BookmarkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsNavigatorViewModel @Inject constructor(
  private val newsUseCases: NewsUseCases
) : ViewModel() {

  private val _state = mutableStateOf(BookmarkState())
  val state: State<BookmarkState> = _state

  init {
    getArticles()
  }

  private fun getArticles() {
    val articles = newsUseCases.getBookmarks().cachedIn(viewModelScope)
    _state.value = _state.value.copy(articles = articles)
  }
}