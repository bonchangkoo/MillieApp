package dev.chamo.millieapp.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.chamo.millieapp.core.data.repository.NewsRepository
import dev.chamo.millieapp.core.data.repository.NewsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    internal abstract fun bindNewsRepository(
        newsRepository: NewsRepositoryImpl
    ): NewsRepository
}