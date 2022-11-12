package com.mertcaliskanyurek.cointicker.data.network

import com.mertcaliskanyurek.cointicker.data.feature.coin.model.Coin
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.CoinDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoingeckoApi {

    @GET("coins/markets")
    suspend fun coinList(@Query("vs_currency") vsCurrency: String): Response<List<Coin>>

    @GET("coins/{id}")
    suspend fun coinDetail(@Path("id") id: String,
                           @Query("localization") locale: String = "false",
                           @Query("tickers") tickers: Boolean = false,
                           @Query("market_data") marketData: Boolean = false,
                           @Query("community_data") communityData: Boolean = true,
                           @Query("developer_data") developerData: Boolean = true,
                           @Query("sparkline") sparkline: Boolean = false
    ): Response<CoinDetail>

    @GET("simple/supported_vs_currencies")
    suspend fun supportedVsCurrencies(): Response<List<String>>

    @GET("simple/price")
    suspend fun prices(@Query("ids") ids: String,
                       @Query("vs_currencies") vsCurrency: String
    ): Response<Map<String,Map<String,Double>>>
}