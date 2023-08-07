package nferno1.cfreddit.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import nferno1.cfreddit.data.locale.DataStoreManager
import nferno1.cfreddit.data.locale.db.CachedSubredditsDao
import nferno1.cfreddit.data.locale.db.CachedThingsDatabase
import nferno1.cfreddit.data.remote.AuthInterceptor
import nferno1.cfreddit.data.remote.RedditApi
import nferno1.cfreddit.data.remote.response.Thing
import nferno1.cfreddit.data.remote.response.comments.CommentsData
import nferno1.cfreddit.data.remote.response.more.MoreData
import nferno1.cfreddit.data.remote.response.posts.PostsData
import nferno1.cfreddit.data.remote.response.subreddits.SubredditsData
import nferno1.cfreddit.data.remote.response.user.UserData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun providesCachedThingsDatabase(@ApplicationContext app: Context): CachedThingsDatabase {
        return Room.databaseBuilder(
            app,
            CachedThingsDatabase::class.java,
            CachedThingsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun providesCachedSubredditDao(db: CachedThingsDatabase): CachedSubredditsDao {
        return db.getCachedSubredditsDao()
    }

    @Provides
    @Singleton
    fun providesPreferencesDataStore(@ApplicationContext app: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { app.preferencesDataStoreFile(DataStoreManager.Base.PREFERENCES_STORE_NAME) }
        )
    }

    @Provides
    @Singleton
    fun providesOkhttpClient(dataStoreManager: DataStoreManager.Base): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(dataStoreManager))
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(Thing::class.java, "kind")
                    .withSubtype(SubredditsData::class.java, "t5")
                    .withSubtype(PostsData::class.java, "t3")
                    .withSubtype(UserData::class.java, "t2")
                    .withSubtype(CommentsData::class.java, "t1")
                    .withSubtype(MoreData::class.java, "more")
            )
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): RedditApi {
        return Retrofit.Builder()
            .baseUrl(RedditApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(RedditApi::class.java)
    }

    @Provides
    fun providesAuthorizationService(@ApplicationContext app: Context): AuthorizationService {
        return AuthorizationService(app)
    }
}