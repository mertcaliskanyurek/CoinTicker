package com.mertcaliskanyurek.cointicker.di.module

import android.content.Context
import com.mertcaliskanyurek.cointicker.data.feature.coin.dao.CoinDAO
import com.mertcaliskanyurek.cointicker.data.feature.coin.dao.CoinWatchlistDAO
import com.mertcaliskanyurek.cointicker.data.feature.coin.repository.CoinRepository
import com.mertcaliskanyurek.cointicker.data.feature.coin.repository.CoinRepositoryImpl
import com.mertcaliskanyurek.cointicker.data.network.CoingeckoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun coinRepository(
        @ApplicationContext context: Context,
        api: CoingeckoApi, coinDAO: CoinDAO,
        coinWatchlistDAO: CoinWatchlistDAO
    ): CoinRepository
            = CoinRepositoryImpl(context,api,coinDAO,coinWatchlistDAO)
}