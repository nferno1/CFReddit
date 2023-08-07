package nferno1.cfreddit.data.remote.response.more

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nferno1.cfreddit.data.remote.response.Thing
import nferno1.cfreddit.data.remote.response.user.UserDto

@JsonClass(generateAdapter = true)
data class MoreData(
    @Json(name = "kind")
    override val kind: String,
    @Json(name = "data")
    override val data: UserDto
) : Thing