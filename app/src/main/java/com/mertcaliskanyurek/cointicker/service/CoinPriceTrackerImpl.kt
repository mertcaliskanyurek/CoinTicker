package com.mertcaliskanyurek.cointicker.service

import com.mertcaliskanyurek.cointicker.data.Resource
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.CoinPricesWrapper
import com.mertcaliskanyurek.cointicker.data.feature.coin.repository.CoinRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CoinPriceTrackerImpl @Inject constructor(
    private val coinRepository: CoinRepository
) : CoinPriceTracker{

    override var priceChangeListener: PriceChangeListener? = null

    companion object {
        private const val UPDATE_PERIOD_IN_SECS: Long = 10
    }

    override fun startTracking() {
        val exec = ScheduledThreadPoolExecutor(1)
        exec.scheduleAtFixedRate(PriceChangeTask(), 1, UPDATE_PERIOD_IN_SECS, TimeUnit.SECONDS)
    }

    inner class PriceChangeTask : Runnable {
        override fun run() {
            listen()
        }

        private fun listen() {
            CoroutineScope(Dispatchers.IO).launch {
                val watchListCoinIds = coinRepository.getWatchListCoinIds()
                val resource: Resource<CoinPricesWrapper> = coinRepository.getCoinPrices(watchListCoinIds)
                if(resource is Resource.Success) {
                    val pricesText = handlePriceUpdateResource(watchListCoinIds, resource.result)
                    CoroutineScope(Dispatchers.Main).launch{
                        priceChangeListener?.onPricesChange(watchListCoinIds, resource.result, pricesText)
                    }
                }
            }
        }

        //returns coin price id:price pair string
        private fun handlePriceUpdateResource(coinIds: List<String>,coinPrices: CoinPricesWrapper): String {
            val stringBuilder = StringBuilder()
            coinIds.forEach { coinId ->
                val price = coinPrices.getPrice(coinId)
                price?.let {
                    stringBuilder.appendLine("$coinId : $it")
                    CoroutineScope(Dispatchers.IO).launch {
                        coinRepository.updateCoinPrice(coinId, it)
                    }
                }
            }
            return stringBuilder.toString()
        }
    } // price change task
}