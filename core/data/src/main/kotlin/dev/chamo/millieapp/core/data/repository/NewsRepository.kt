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
}

class NewsRepositoryImpl @Inject constructor(
    private val millieAppNetworkDataSource: MillieAppNetworkDataSource,
    private val topHeadlineDao: TopHeadlineDao,
    @Dispatcher(MillieAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : NewsRepository {

    override fun getTopHeadlines(isOffline: Boolean): Flow<List<TopHeadline>> {
        return flow {
            if (isOffline) {
                val headlineEntities = topHeadlineDao.getTopHeadlineEntities()
                val topHeadLines = headlineEntities.map { entities ->
                    entities.asExternalModel()
                }
                emit(topHeadLines)

            } else {
                val networkTopHeadlines = millieAppNetworkDataSource.getTopHeadlines()
                val topHeadLines = networkTopHeadlines.asExternalModel()
                emit(topHeadLines)

                val headlineEntities = networkTopHeadlines.asEntity()
                insertTopHeadlines(headlineEntities)
            }
        }.flowOn(ioDispatcher)
    }

    private suspend fun insertTopHeadlines(headlineEntities: List<TopHeadlineEntity>) {
        topHeadlineDao.insertOrReplaceTopHeadlines(
            headlineEntities = headlineEntities
        )
    }
}
