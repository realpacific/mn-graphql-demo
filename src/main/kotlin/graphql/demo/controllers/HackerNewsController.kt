package graphql.demo.controllers

import graphql.demo.services.HackerNewsService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import javax.inject.Inject

@Controller
class HackerNewsController {

    @Inject
    private lateinit var service: HackerNewsService

    @Get("/news/{id}")
    fun getPosts(@PathVariable id: Int) = service.getNewsById(id)

}
