package com.mertcaliskanyurek.cointicker.di.module

import com.mertcaliskanyurek.cointicker.data.network.CoingeckoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    const val BASE_URL = "https://api.coingecko.com/api/v3/"

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun coingeckoApi(retrofit: Retrofit): CoingeckoApi = retrofit.create(CoingeckoApi::class.java)
}