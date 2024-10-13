package dev.chamo.millieapp.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.chamo.millieapp.core.network.BuildConfig
import dev.chamo.millieapp.core.network.MillieAppNetworkDataSource
import dev.chamo.millieapp.core.network.model.NetworkTopHeadlines
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject

private const val BASE_URL = BuildConfig.BASE_URL
private const val API_KEY = BuildConfig.API_KEY

class RetrofitMillieAppNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
): MillieAppNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(RetrofitMillieAppNetworkApi::class.java)

    override suspend fun getTopHeadlines(): NetworkTopHeadlines {
        return networkApi.getTopHeadlines(
            apiKey = API_KEY,
            country = "us"
        )
    }
}