package com.mertcaliskanyurek.cointicker.data.feature.coin.dao

import androidx.room.*
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.Coin

@Dao
interface CoinDAO : BaseDAO<Coin>{

    @Query("SELECT * FROM coins WHERE id = :id")
    fun getCoin(id: String): Coin?

    @Query("SELECT * FROM coins WHERE name LIKE '%' || :search || '%' ")
    fun search(search: String): List<Coin>?

    @Query("UPDATE coins SET current_price = :price WHERE id = :id")
    fun updatePrice(id: String, price: Double)

}