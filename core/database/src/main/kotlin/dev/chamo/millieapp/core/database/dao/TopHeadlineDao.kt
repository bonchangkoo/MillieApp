package dev.chamo.millieapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.chamo.millieapp.core.database.model.TopHeadlineEntity

@Dao
interface TopHeadlineDao {
    @Query(value = "SELECT * FROM top_headlines")
    suspend fun getTopHeadlineEntities(): List<TopHeadlineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceTopHeadlines(headlineEntities: List<TopHeadlineEntity>): List<Long>
}