package graphql.demo.models

import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

abstract class News {
    abstract var by: String?
    abstract var id: Int
    abstract var text: String?
    abstract var time: Long
    abstract var type: String?
}

data class Story(
        override var by: String?,
        var descendants: Int,
        override var id: Int,
        var kids: List<Int>?,
        var score: Int,
        override var time: Long,
        var title: String,
        override var type: String?,
        var url: String?
) : News() {
    override var text: String? = null
        get() = title
        set(value) {
            field = value
        }

    fun toComment(parent: Int) = Comment(parent, by, id, title, time, type, kids)
}

@Entity
data class Comment(
        var parent: Int?,
        override var by: String?,
        @Id override var id: Int,
        @Column(length = 5000) override var text: String?,
        override var time: Long,
        override var type: String?,
        @Cascade(CascadeType.ALL) @ElementCollection(fetch = FetchType.EAGER) val kids: List<Int>? = mutableListOf()
) : News() {

    constructor() : this(null, null, -1, null, 0, null)

    fun toNewsVM() = NewsVM(parent, by, id, text, time, type)

}

data class NewsVM(
        var parent: Int?,
        override var by: String?,
        override var id: Int,
        override var text: String?,
        override var time: Long,
        override var type: String?
) : News() {
    constructor() : this(null, null, -1, null, 0, null)

    var comments: MutableList<NewsVM> = mutableListOf()

}

fun NewsVM.toComments(): List<Comment> {
    val result = mutableListOf<Comment>()

    fun innerLoop(newsVM: NewsVM) {
        newsVM.comments.forEach {
            result.add(Comment(it.parent, it.by, it.id, it.text, it.time, it.type, it.comments.map { comment -> comment.id }))
            innerLoop(it)
        }
    }
    result.add(Comment(parent, by, id, text, time, type, comments.map { comment -> comment.id }))
    innerLoop(this)
    return result
}
