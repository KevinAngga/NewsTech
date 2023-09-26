package com.fjr619.newsloc.domain.usecase.news

import com.fjr619.newsloc.data.local.NewsDao
import com.fjr619.newsloc.domain.model.Article
import javax.inject.Inject

class DeleteArticle @Inject constructor(
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(article: Article){
        newsDao.delete(article = article)
    }

}