package com.fjr619.newsloc.presentation.detail

import com.fjr619.newsloc.domain.model.Article
import com.fjr619.newsloc.domain.model.Source
import com.fjr619.newsloc.util.UiText
import com.fjr619.newsloc.util.composestateevents.StateEventWithContent
import com.fjr619.newsloc.util.composestateevents.consumed

data class DetailViewState(
  val article: Article = Article(
    author = "",
    content = "",
    publishedAt = "",
    source = Source("", ""),
    title = "",
    url = ""
  ),
  val bookmark: Article? = null,
  val processSucceededEvent: StateEventWithContent<UiText> = consumed()
)