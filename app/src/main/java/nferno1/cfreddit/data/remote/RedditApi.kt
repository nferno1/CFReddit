package nferno1.cfreddit.data.remote

import nferno1.cfreddit.data.remote.response.CommonResponse
import nferno1.cfreddit.data.remote.response.Thing
import nferno1.cfreddit.data.remote.response.user.UserDto
import retrofit2.Response
import retrofit2.http.*

interface RedditApi {
    companion object {
        const val BASE_URL = "https://oauth.reddit.com/"
        const val userName = "userName"
        const val postId = "postId"
        const val subredditName = "subredditName"
    }

    @GET("/subreddits/{type}")
    suspend fun loadSubreddits(
        @Path("type") type: String,
        @Query("after") afterKey: String
    ): Response<CommonResponse>

    @GET("/subreddits/search")
    suspend fun searchSubreddits(
        @Query("q") query: String,
        @Query("after") afterKey: String
    ): Response<CommonResponse>

    @GET("/r/{subredditName}")
    suspend fun loadSubredditPosts(
        @Path("subredditName") subredditName: String,
        @Query("after") afterKey: String
    ): Response<CommonResponse>

    @GET("/comments/{postId}")
    suspend fun getComments(
        @Path("postId") pstId: String
    ): Response<List<CommonResponse>>

    @POST("/api/vote")
    suspend fun vote(
        @Query("dir") direction: Int,
        @Query("id") id: String
    ): Response<Unit>

    @POST("/api/subscribe")
    suspend fun subscribeUnsubscribe(
        @Query("action") action: String,
        @Query("sr_name") displayName: String
    ): Response<Unit>

    @GET("user/{username}/about")
    suspend fun getUserInfo(
        @Path("username") userName: String
    ): Response<Thing>

    @GET("/subreddits/mine/subscriber")
    suspend fun loadFavoriteSubreddits(
        @Query("after") afterKey: String
    ): Response<CommonResponse>

    @GET("/user/{userName}/saved")
    suspend fun loadFavoritePosts(
        @Path("userName") userName: String,
        @Query("after") after: String,
        @Query("type") type: String = "links"
    ): Response<CommonResponse>

    @GET("api/v1/me")
    suspend fun getMyProfile(): Response<UserDto>

}