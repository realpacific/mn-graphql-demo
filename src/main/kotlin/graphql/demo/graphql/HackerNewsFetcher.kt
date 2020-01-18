package graphql.demo.graphql

import graphql.demo.models.NewsVM
import graphql.demo.services.HackerNewsService
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HackerNewsFetcher : DataFetcher<NewsVM> {
    @Inject
    lateinit var service: HackerNewsService

    override fun get(environment: DataFetchingEnvironment?): NewsVM {
        val id = environment!!.getArgument<String>("id")
        return service.getNewsById(id.toInt())
    }
}
