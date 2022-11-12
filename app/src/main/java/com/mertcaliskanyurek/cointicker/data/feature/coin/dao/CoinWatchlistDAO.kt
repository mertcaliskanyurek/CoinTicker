package com.mertcaliskanyurek.cointicker.data.feature.coin.dao

import androidx.room.Dao
import androidx.room.Query
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.Coin
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.CoinWatchlist

@Dao
interface CoinWatchlistDAO : BaseDAO<CoinWatchlist> {

    @Query("SELECT COUNT(*) FROM coin_watch_list WHERE coinId = :coinId")
    fun count(coinId: String): Int

    @Query("SELECT coinId FROM coin_watch_list")
    fun getWatchListCoinIds(): List<String>

    @Query("SELECT * FROM coins WHERE id IN(:watchListIds)")
    fun getWatchListCoins(watchListIds: List<String>): List<Coin>

    @Query("DELETE FROM coin_watch_list WHERE coinId = :coinId")
    suspend fun delete(coinId: String)
}