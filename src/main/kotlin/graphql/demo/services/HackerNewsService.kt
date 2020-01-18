package graphql.demo.services

import graphql.demo.models.NewsVM

interface HackerNewsService {

    fun updateTextById(id: Int, text: String): NewsVM

    fun getNewsById(id: Int): NewsVM
}
