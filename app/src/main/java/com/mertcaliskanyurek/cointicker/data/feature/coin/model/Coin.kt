package com.mertcaliskanyurek.cointicker.data.feature.coin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class Coin(
    @PrimaryKey val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    var current_price: Double,
    val market_cap: Double,
    val market_cap_rank: Int,
    val total_volume: Double,
    val high_24h: Double,
    val low_24h: Double,
    val price_change_24h: Double,
    val price_change_percentage_24h: Double,
    val market_cap_change_24h: Double,
    val market_cap_change_percentage_24h: Double,
    val circulating_supply: Double,
    val total_supply: Double,
    val ath: Double,
    val ath_change_percentage: Double,
    val atl: Double,
    val atl_change_percentage: Double,
    val last_updated: String,
)