package dev.chamo.millieapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.chamo.millieapp.core.database.dao.TopHeadlineDao
import dev.chamo.millieapp.core.database.model.TopHeadlineEntity

@Database(
    entities = [
        TopHeadlineEntity::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class MillieAppDatabase : RoomDatabase() {
    abstract fun topHeadlineDao(): TopHeadlineDao
}