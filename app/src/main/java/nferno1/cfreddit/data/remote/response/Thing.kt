package nferno1.cfreddit.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

interface Thing {

    val kind: String
    val data: MapData
}

@JsonClass(generateAdapter = true)
data class CommonResponse(
    @Json(name = "data")
    val commonData: CommonData,
    @Json(name = "kind")
    val kind: String
)

@JsonClass(generateAdapter = true)
data class CommonData(
    @Json(name = "after")
    val after: String?,
    @Json(name = "before")
    val before: String?,
    @Json(name = "children")
    val data: List<Thing>
)
