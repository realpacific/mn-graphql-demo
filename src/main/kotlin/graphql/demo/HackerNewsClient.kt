package graphql.demo

import graphql.demo.models.Comment
import graphql.demo.models.Story
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client("https://hacker-news.firebaseio.com")
interface HackerNewsClient {
    @Get("/v0/item/{id}.json")
    fun getStory(id: Int): Story

    @Get("/v0/item/{id}.json")
    fun getComment(id: Int): Comment
}
