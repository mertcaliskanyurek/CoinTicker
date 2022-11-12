package com.mertcaliskanyurek.cointicker.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.mertcaliskanyurek.cointicker.R
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.CoinPricesWrapper
import com.mertcaliskanyurek.cointicker.notification.NotificationStrategy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


@AndroidEntryPoint
class CoinPriceService : Service() {

    companion object {
        private const val NOTIFICATION_ID = 1
    }

    @Inject lateinit var notificationStrategy: NotificationStrategy
    @Inject lateinit var coinPriceTracker: CoinPriceTracker

    var priceChangeListener: ServicePriceChangeListener? = null

    private val binder = CoinPriceServiceBinder()

    override fun onCreate() {
        super.onCreate()
        coinPriceTracker.priceChangeListener =
            PriceChangeListener { coinIds, prices, pricesText ->
                val notification: Notification? = notificationStrategy.buildNotification(
                    getString(R.string.notification_channel_title),
                    pricesText,
                    R.drawable.ic_eye_on
                )
                notificationStrategy.notify(NOTIFICATION_ID, notification)
                priceChangeListener?.onPricesChange(coinIds,prices)
            }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification: Notification? = notificationStrategy.buildNotification(
                getString(R.string.notification_channel_title),
                "",
                R.drawable.ic_eye_on
            )
            coinPriceTracker.startTracking()
            startForeground(NOTIFICATION_ID, notification)
            return START_NOT_STICKY
        }
        coinPriceTracker.startTracking()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class CoinPriceServiceBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): CoinPriceService = this@CoinPriceService
    }

    fun interface ServicePriceChangeListener {
        fun onPricesChange(coinIds: List<String>, prices: CoinPricesWrapper)
    }
}