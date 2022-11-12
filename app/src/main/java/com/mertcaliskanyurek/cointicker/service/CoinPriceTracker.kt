package com.mertcaliskanyurek.cointicker.service

import com.mertcaliskanyurek.cointicker.data.feature.coin.model.CoinPricesWrapper

interface CoinPriceTracker {
    var priceChangeListener: PriceChangeListener?
    fun startTracking()
}

fun interface PriceChangeListener {
    fun onPricesChange(coinIds: List<String>, prices: CoinPricesWrapper, pricesText: String)
}