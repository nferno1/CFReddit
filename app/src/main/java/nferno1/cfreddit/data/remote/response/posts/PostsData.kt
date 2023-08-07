package nferno1.cfreddit.data.remote.response.posts

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nferno1.cfreddit.data.remote.response.Thing

@JsonClass(generateAdapter = true)
data class PostsData(
    @Json(name = "kind")
    override val kind: String,
    @Json(name = "data")
    override val data: PostDto
) : Thing