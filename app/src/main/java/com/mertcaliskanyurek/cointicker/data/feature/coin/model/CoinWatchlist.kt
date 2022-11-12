package com.mertcaliskanyurek.cointicker.data.feature.coin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "coin_watch_list",
    foreignKeys = [
        ForeignKey(
            entity = Coin::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("coinId"),
        )
    ]
)
data class CoinWatchlist(
    @PrimaryKey(autoGenerate = true) val _id: Long = 0,
    @ColumnInfo(index = true)
    val coinId : String,
    val dateAdded: String,
)