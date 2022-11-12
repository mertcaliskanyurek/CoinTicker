package com.mertcaliskanyurek.cointicker.di.module

import android.content.Context
import androidx.room.Room
import com.mertcaliskanyurek.cointicker.data.AppDatabase
import com.mertcaliskanyurek.cointicker.data.feature.coin.dao.CoinDAO
import com.mertcaliskanyurek.cointicker.data.feature.coin.dao.CoinDetailDAO
import com.mertcaliskanyurek.cointicker.data.feature.coin.dao.CoinWatchlistDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun appDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "bitcointicker.db").build()

    @Provides
    @Singleton
    fun coinDao(db: AppDatabase): CoinDAO = db.coinDAO()

    @Provides
    @Singleton
    fun coinDetailDao(db: AppDatabase): CoinDetailDAO = db.coinDetailDAO()

    @Provides
    @Singleton
    fun coinWatchlistDao(db: AppDatabase): CoinWatchlistDAO = db.coinWatchlistDAO()

}