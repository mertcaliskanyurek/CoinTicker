package com.mertcaliskanyurek.cointicker.ui.feature.main

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mertcaliskanyurek.cointicker.R
import com.mertcaliskanyurek.cointicker.data.providers.CoinSuggestionProvider
import com.mertcaliskanyurek.cointicker.databinding.ActivityMainBinding
import com.mertcaliskanyurek.cointicker.ui.feature.coins.CoinRecyclerListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CoinRecyclerListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.nav_watchlist,
            )
        )
        val navController: NavController = findNavController(R.id.nav_host_fragment_activity_main)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> navController.navigate(R.id.navigation_home).also { initActionBar(R.string.all_coins) }
                R.id.nav_watchlist -> navController.navigate(R.id.navigation_watchlist).also { initActionBar(R.string.watch_list) }
            }
            true
        }

        initActionBar(R.string.all_coins)
        handleNewIntent(intent)
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleNewIntent(it) }
    }

    private fun initActionBar(@StringRes resId:Int) = supportActionBar?.apply {
        title = getString(resId)
    }

    private fun handleNewIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(this, CoinSuggestionProvider.AUTHORITY, CoinSuggestionProvider.MODE)
                    .saveRecentQuery(query, null)
                viewModel.searchCoins(query)
            }
        }
    }
}