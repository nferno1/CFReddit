package nferno1.cfreddit.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import nferno1.cfreddit.data.remote.response.CommonResponse
import nferno1.cfreddit.data.remote.response.Thing
import retrofit2.Response
import javax.inject.Inject

class GenericPagingSource @Inject constructor(
    private val apiRequest: suspend (String) -> Response<CommonResponse>
) : PagingSource<String, Thing>() {

    override val keyReuseSupported = true

    override fun getRefreshKey(state: PagingState<String, Thing>): String = ""

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Thing> {

        val after = params.key ?: ""
        return kotlin.runCatching {
            apiRequest(after)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it.body()?.commonData?.data
                        ?: emptyList(),
                    prevKey = null,
                    nextKey = it.body()?.commonData?.after
                )
            },
            onFailure = { throw it }
        )
    }
}