package com.mertcaliskanyurek.cointicker.ui.feature.coins

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCoinsListFragment : CoinRecyclerListFragment() {

    override fun onRefreshRequest() {
        sharedViewModel.fetchCoins()
    }
}