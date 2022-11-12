package com.mertcaliskanyurek.cointicker.ui.feature.coin_detail

import com.mertcaliskanyurek.cointicker.data.Resource
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.CoinDetail

data class CoinDetailState(
    val coinDetails: Resource<CoinDetail>,
    val watchList: Boolean
)