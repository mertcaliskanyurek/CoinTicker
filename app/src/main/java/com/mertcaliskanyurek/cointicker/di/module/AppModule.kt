package com.mertcaliskanyurek.cointicker.di.module

import android.content.Context
import android.os.Build
import com.mertcaliskanyurek.cointicker.data.feature.coin.repository.CoinRepository
import com.mertcaliskanyurek.cointicker.providers.ResourceProvider
import com.mertcaliskanyurek.cointicker.providers.ResourceProviderImpl
import com.mertcaliskanyurek.cointicker.notification.NotificationStrategy
import com.mertcaliskanyurek.cointicker.notification.NotificationStrategyAboveApi26
import com.mertcaliskanyurek.cointicker.notification.NotificationStrategyBelowApi26
import com.mertcaliskanyurek.cointicker.service.CoinPriceTracker
import com.mertcaliskanyurek.cointicker.service.CoinPriceTrackerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun resourceProvider(@ApplicationContext context: Context): ResourceProvider = ResourceProviderImpl(context)

    @Provides
    fun notificationStrategy(@ApplicationContext context: Context): NotificationStrategy =
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) NotificationStrategyAboveApi26(context)
        else NotificationStrategyBelowApi26(context)

    @Provides
    fun coinPriceTracker(coinRepository: CoinRepository): CoinPriceTracker = CoinPriceTrackerImpl(coinRepository)
}