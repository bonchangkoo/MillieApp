package dev.chamo.millieapp.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.chamo.millieapp.core.network.MillieAppNetworkDataSource
import dev.chamo.millieapp.core.network.retrofit.RetrofitMillieAppNetwork
import dev.chamo.mycletest.core.network.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
        .build()

    @Provides
    @Singleton
    fun providesMillieAppNetworkDataSource(
        networkJson: Json,
        okhttpCallFactory: Call.Factory
    ): MillieAppNetworkDataSource {
        return RetrofitMillieAppNetwork(
            networkJson = networkJson,
            okhttpCallFactory = okhttpCallFactory
        )
    }
}