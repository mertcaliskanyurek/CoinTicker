package com.mertcaliskanyurek.cointicker.ui.feature.coins

import com.mertcaliskanyurek.cointicker.data.Resource
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.Coin

data class CoinsState(
    val coins: Resource<List<Coin>?>,
    val watchListIds: ArrayList<String>
)
