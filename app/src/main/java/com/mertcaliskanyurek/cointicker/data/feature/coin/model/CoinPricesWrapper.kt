package com.mertcaliskanyurek.cointicker.data.feature.coin.model

class CoinPricesWrapper {
    //key: coinId val: coin price
    private val prices: HashMap<String, Double> = HashMap()

    fun addPrice(coinId: String, price: Double) = prices.put(coinId, price)
    fun getPrice(coinId: String): Double? = prices[coinId]
}
