package graphql.demo.services.impl

import graphql.demo.HackerNewsClient
import graphql.demo.models.Comment
import graphql.demo.models.NewsVM
import graphql.demo.repositories.HackerNewsRepository
import graphql.demo.services.HackerNewsService
import io.micronaut.context.annotation.Primary
import javax.inject.Inject
import javax.inject.Singleton

@Primary
@Singleton
class HackerNewsServiceImpl : HackerNewsService {
    @Inject
    private lateinit var client: HackerNewsClient

    @Inject
    private lateinit var repository: HackerNewsRepository

    override fun getNewsById(id: Int): NewsVM {
        val root = client.getStory(id)
        val comment = root.toComment(id)
        val response = comment.toNewsVM()
        fetchChildrenFromNetwork(comment, response)
        return response
    }


    private fun fetchChildrenFromNetwork(story: Comment, result: NewsVM) {
        story.kids?.forEachIndexed loop@{ index, it ->
            if (index > 3) {
                return@loop
            }
            if (!repository.existsById(it)) {
                val comment = client.getComment(it)
                repository.save(comment)
                val commentVM = comment.toNewsVM()
                result.comments.add(commentVM)
                fetchChildrenFromNetwork(comment, commentVM)
            } else {
                val comment = repository.find(it)!!
                val commentVM = comment.toNewsVM()
                result.comments.add(commentVM)
                fetchChildrenFromNetwork(comment, commentVM)
            }
        }
    }

    override fun updateTextById(id: Int, text: String): NewsVM {
        val comment = repository.find(id) ?: throw RuntimeException("Not found.")
        repository.update(id, text)
        return repository.find(id)!!.toNewsVM()
    }
}
