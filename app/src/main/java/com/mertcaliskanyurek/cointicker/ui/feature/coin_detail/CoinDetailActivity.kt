package com.mertcaliskanyurek.cointicker.ui.feature.coin_detail

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mertcaliskanyurek.cointicker.R
import com.mertcaliskanyurek.cointicker.data.Resource
import com.mertcaliskanyurek.cointicker.databinding.ActivityCoinDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_COIN_ID = "EXTRA_COIN_ID"
    }

    private lateinit var binding: ActivityCoinDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.getStringExtra(EXTRA_COIN_ID) == null) {
            finish()
            return
        }
        val coinId = intent.getStringExtra(EXTRA_COIN_ID)!!
        val detailViewModel: CoinDetailViewModel by viewModels()

        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar)).also {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }
        lifecycleScope.launch {
            detailViewModel.stateFlow.collect { uiState ->
                binding.watchList = uiState.watchList
                binding.progressBar.visibility = if(uiState.coinDetails is Resource.Loading) View.VISIBLE else View.GONE
                if (uiState.coinDetails is Resource.Success) {
                    binding.coinDetail = uiState.coinDetails.result
                    Glide.with(baseContext).load(uiState.coinDetails.result.image.large).into(binding.ivToolbarCoin)

                    binding.apply {
                        val movementInstance = LinkMovementMethod.getInstance()
                        tvDescription.movementMethod = movementInstance
                        tvTwitterUrl.movementMethod = movementInstance
                        tvSubredditUrl.movementMethod = movementInstance
                        tvChatUrl.movementMethod = movementInstance
                        tvAnnouncementUrl.movementMethod = movementInstance
                        tvBlockchainUrl.movementMethod = movementInstance
                        tvFormUrl.movementMethod = movementInstance
                        tvHomepageUrl.movementMethod = movementInstance
                    }
                }
                else if(uiState.coinDetails is Resource.Failure) {
                    Toast.makeText(baseContext, uiState.coinDetails.exception.message, Toast.LENGTH_SHORT).show()
                    finish()
                }

            }
        }

        binding.fab.setOnClickListener { view ->
            detailViewModel.stateFlow.value.watchList.let { isInWatchList ->
                binding.watchList = !isInWatchList
                Toast.makeText(baseContext,if(isInWatchList)
                    R.string.removed_from_watchlist else R.string.added_to_watchlist,
                    Toast.LENGTH_SHORT).show()
            }
            detailViewModel.toggleWatchList(coinId)
        }

        detailViewModel.fetchCoinDetail(coinId)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}