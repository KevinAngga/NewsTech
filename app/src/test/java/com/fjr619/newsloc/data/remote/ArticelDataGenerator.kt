package com.fjr619.newsloc.data.remote

import com.fjr619.newsloc.domain.model.Article
import com.fjr619.newsloc.domain.model.Source

val articles = listOf(
  Article(
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
  ),

  Article(
    author = "author2",
    content = "content",
    description = "description",
    publishedAt = "publishAt",
    source = Source(
      id = "2",
      name = "name"
    ),
    title = "title2",
    url = "url",
    urlToImage = "urlToImage"
  ),

  Article(
    author = "author3",
    content = "content",
    description = "description",
    publishedAt = "publishAt",
    source = Source(
      id = "3",
      name = "name"
    ),
    title = "title3",
    url = "url",
    urlToImage = "urlToImage"
  )
)