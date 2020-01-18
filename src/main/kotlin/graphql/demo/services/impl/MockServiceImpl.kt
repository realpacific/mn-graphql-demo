package graphql.demo.services.impl

import com.fasterxml.jackson.databind.ObjectMapper
import graphql.demo.models.NewsVM
import graphql.demo.models.toComments
import graphql.demo.repositories.HackerNewsRepository
import graphql.demo.services.HackerNewsService
import io.micronaut.context.annotation.Primary
import io.micronaut.core.io.ResourceResolver
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockServiceImpl : HackerNewsService {

    @Inject
    private lateinit var resourceResolver: ResourceResolver

    @Inject
    private lateinit var repository: HackerNewsRepository

    override fun getNewsById(id: Int): NewsVM {
        println("Getting mock response")
        val mapper = ObjectMapper()
        val list = mapper
                .readValue(resourceResolver.getResourceAsStream("classpath:test.json").get(), NewsVM::class.java)
        list.toComments().forEach {
            if (!repository.existsById(it.id)) repository.save(it)
        }
        return list
    }

    @PostConstruct
    fun onLoad() {
        getNewsById(126809)
    }

    override fun updateTextById(id: Int, text: String): NewsVM {
        TODO()
    }
}
