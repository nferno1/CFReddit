package nferno1.cfreddit.domain.model

data class CommentEntity(
    override val id: String,
    val author: String,
    val body: String,
    val isLikedByUser: Boolean?,
    val score: String,
    val prefixedId: String,
    val saved: Boolean
) : AbstractRedditEntity()