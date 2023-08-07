package nferno1.cfreddit.data.remote.response.user


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nferno1.cfreddit.domain.model.UserEntity
import nferno1.cfreddit.domain.toDateTimeFormat
import nferno1.cfreddit.data.remote.response.MapData

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "created")
    val created: Long,
    @Json(name = "icon_img")
    val iconImg: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "total_karma")
    val totalKarma: Int,
) : MapData {

    override fun map(): UserEntity {
        return UserEntity(
            id = id,
            name = name,
            karma = totalKarma,
            created = (created * 1000).toDateTimeFormat(),
            iconImg = iconImg
        )
    }

}