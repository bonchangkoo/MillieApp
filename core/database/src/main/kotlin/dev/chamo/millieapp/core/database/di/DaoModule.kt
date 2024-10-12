package dev.chamo.millieapp.core.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.chamo.millieapp.core.database.MillieAppDatabase
import dev.chamo.millieapp.core.database.dao.TopHeadlineDao

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun providesTopicsDao(
        database: MillieAppDatabase,
    ): TopHeadlineDao = database.topHeadlineDao()
}