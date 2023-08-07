package nferno1.cfreddit.domain.model

data class UserEntity(
    override val id: String,
    val name: String,
    val karma: Int,
    val created: String,
    val iconImg: String
) : AbstractRedditEntity()