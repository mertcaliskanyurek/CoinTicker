package com.mertcaliskanyurek.cointicker.ui.feature.coins

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import com.google.android.material.snackbar.Snackbar
import com.mertcaliskanyurek.cointicker.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchlistCoinsListFragment : CoinRecyclerListFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nextWatchListClickHandler = { item, position, added ->
            if (added) coinRecyclerAdapter.restoreItem(item, position)
            else coinRecyclerAdapter.removeItem(position)

            Snackbar.make(binding.root,
                if (added) R.string.added_to_watchlist else R.string.removed_from_watchlist,
                Snackbar.LENGTH_LONG)
                .setAction(R.string.undo) {
                    coinRecyclerAdapter.watchListClickListener?.onToggleToWatchlistClick(item, position)
                }.show()
        }
    }

    override fun onRefreshRequest() {
        sharedViewModel.fetchWatchList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.search).apply { isVisible = false }
        menu.findItem(R.id.vs_currency).apply { isVisible = false }
    }
}