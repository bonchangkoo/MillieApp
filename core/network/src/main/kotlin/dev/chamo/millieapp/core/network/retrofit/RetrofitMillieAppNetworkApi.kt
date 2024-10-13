package dev.chamo.millieapp.core.network.retrofit

import dev.chamo.millieapp.core.network.model.NetworkTopHeadlines
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitMillieAppNetworkApi {
    @GET(value = "/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String
    ): NetworkTopHeadlines
}