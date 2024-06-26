package com.example.e_commerceapp.network

import com.example.e_commerceapp.util.Constants.BASE_URL
import com.example.e_commerceapp.util.Constants.TIME_OUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClient {

    @Singleton
    @Provides
    fun create() : ApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private fun client(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return with(OkHttpClient.Builder()) {
            addInterceptor(loggingInterceptor)
            retryOnConnectionFailure(false)
            callTimeout(TIME_OUT, TimeUnit.SECONDS)
            connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            readTimeout(TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            this.build()
        }
    }

}