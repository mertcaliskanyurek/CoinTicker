package com.mertcaliskanyurek.cointicker.data.feature.coin.repository

import com.mertcaliskanyurek.cointicker.data.Resource
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.Coin
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.CoinDetail
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.CoinPricesWrapper

interface CoinRepository {
    var vsCurrency: String
    fun getCoin(id:String): Coin?
    fun isInWatchList(id: String): Boolean
    fun getWatchListCoinIds(): List<String>
    fun getWatchListCoins(watchListIds: List<String>): List<Coin>
    fun searchCoin(filter: String): Resource<List<Coin>?>
    suspend fun getCoinPrices(ids: List<String>): Resource<CoinPricesWrapper>
    suspend fun getCoins(): Resource<List<Coin>>
    suspend fun getCoinDetail(id: String): Resource<CoinDetail>
    suspend fun toggleWatchList(coindId: String)
    suspend fun updateCoinPrice(id: String, price: Double)
    suspend fun getSupportedVsCurrencies(): Resource<List<String>>
}