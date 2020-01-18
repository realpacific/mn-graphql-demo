package graphql.demo.repositories


import graphql.demo.models.Comment
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface HackerNewsRepository : CrudRepository<Comment, Int> {
    fun find(id: Int): Comment?


    @Query("UPDATE Comment c SET c.text=:text WHERE c.id=:id")
    fun update(id: Int, text: String)
}
