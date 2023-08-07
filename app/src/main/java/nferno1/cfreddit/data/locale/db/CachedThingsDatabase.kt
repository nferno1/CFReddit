package nferno1.cfreddit.data.locale.db

import androidx.room.Database
import androidx.room.RoomDatabase
import nferno1.cfreddit.domain.model.SubredditEntity

@Database(
    entities = [SubredditEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CachedThingsDatabase : RoomDatabase() {

    abstract fun getCachedSubredditsDao(): CachedSubredditsDao

    companion object {
        const val DATABASE_NAME = "cached_reddit_things"
    }

}