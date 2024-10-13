package dev.chamo.millieapp.core.data.repository

import dev.chamo.millieapp.core.common.network.Dispatcher
import dev.chamo.millieapp.core.common.network.MillieAppDispatchers
import dev.chamo.millieapp.core.data.model.asEntity
import dev.chamo.millieapp.core.data.model.asExternalModel
import dev.chamo.millieapp.core.database.dao.TopHeadlineDao
import dev.chamo.millieapp.core.database.model.TopHeadlineEntity
import dev.chamo.millieapp.core.database.model.asExternalModel
import dev.chamo.millieapp.core.model.TopHeadline
import dev.chamo.millieapp.core.network.MillieAppNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface NewsRepository {
    fun getTopHeadlines(isOffline: Boolean = false): Flow<List<TopHeadline>>
    suspend fun upsertTopHeadLine(topHeadline: TopHeadline)
}

class NewsRepositoryImpl @Inject constructor(
    private val millieAppNetworkDataSource: MillieAppNetworkDataSource,
    private val topHeadlineDao: TopHeadlineDao,
    @Dispatcher(MillieAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : NewsRepository {

    override fun getTopHeadlines(isOffline: Boolean): Flow<List<TopHeadline>> {
        return flow {
            val topHeadlineEntityList = topHeadlineDao.getTopHeadlineEntities()
            val selectedTitles = topHeadlineEntityList.filter {
                it.isSelected
            }.map {
                it.title
            }

            if (isOffline) {
                val topHeadLines = topHeadlineEntityList.asExternalModel(
                    selectedTitles = selectedTitles
                )
                emit(topHeadLines)

            } else {
                val networkTopHeadlines = millieAppNetworkDataSource.getTopHeadlines()

                val topHeadLines = networkTopHeadlines.asExternalModel(
                    selectedTitles = selectedTitles
                )
                emit(topHeadLines)

                val topHeadlineEntities = networkTopHeadlines.asEntity()
                insertTopHeadlines(topHeadlineEntities)
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun upsertTopHeadLine(topHeadline: TopHeadline) {
        val topHeadlineEntity = topHeadline.asEntity()
        topHeadlineDao.upsertTopHeadline(topHeadlineEntity)
    }

    private suspend fun insertTopHeadlines(topHeadlineEntities: List<TopHeadlineEntity>) {
       topHeadlineDao.insertOrReplaceTopHeadlines(
            topHeadlineEntities = topHeadlineEntities
        )
    }
}
