package dev.chamo.millieapp.core.data.repository

import dev.chamo.millieapp.core.common.network.Dispatcher
import dev.chamo.millieapp.core.common.network.MillieAppDispatchers
import dev.chamo.millieapp.core.data.model.asExternalModel
import dev.chamo.millieapp.core.model.TopHeadLine
import dev.chamo.millieapp.core.network.MillieAppNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface NewsRepository {
    fun getTopHeadlines(): Flow<List<TopHeadLine>>
}

class NewsRepositoryImpl @Inject constructor(
    private val millieAppNetworkDataSource: MillieAppNetworkDataSource,
    @Dispatcher(MillieAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : NewsRepository {
    override fun getTopHeadlines(): Flow<List<TopHeadLine>> {
        return flow {
            emit(
                millieAppNetworkDataSource.getTopHeadlines()
                    .asExternalModel()
            )
        }.flowOn(ioDispatcher)
    }
}