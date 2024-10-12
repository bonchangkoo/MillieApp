package dev.chamo.millieapp.core.network

import dev.chamo.millieapp.core.network.model.NetworkTopHeadlines

interface MillieAppNetworkDataSource {
    suspend fun getTopHeadlines(): NetworkTopHeadlines
}