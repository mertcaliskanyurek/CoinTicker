package com.mertcaliskanyurek.cointicker.ui.feature.coins

import android.app.AlertDialog
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mertcaliskanyurek.cointicker.R
import com.mertcaliskanyurek.cointicker.data.Resource
import com.mertcaliskanyurek.cointicker.data.adapters.CoinRecyclerAdapter
import com.mertcaliskanyurek.cointicker.data.adapters.GenericRecyclerAdapter
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.Coin
import com.mertcaliskanyurek.cointicker.databinding.FragmentListBinding
import com.mertcaliskanyurek.cointicker.service.CoinPriceService
import com.mertcaliskanyurek.cointicker.ui.feature.coin_detail.CoinDetailActivity
import kotlinx.coroutines.launch

abstract class CoinRecyclerListFragment : Fragment() {

    protected lateinit var binding: FragmentListBinding

    protected val coinRecyclerAdapter = CoinRecyclerAdapter(ArrayList())
    protected val sharedViewModel: CoinRecyclerListViewModel by activityViewModels()

    protected var nextWatchListClickHandler: ((item: Coin, position: Int, added: Boolean)-> Unit)? = null

    private lateinit var coinPriceService: CoinPriceService
    //private var isServiceBound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        coinRecyclerAdapter.itemClickListener =
            GenericRecyclerAdapter.ItemClickListener<Coin> { item, position ->
                val intent = Intent(context, CoinDetailActivity::class.java)
                intent.putExtra(CoinDetailActivity.EXTRA_COIN_ID,item.id)
                startActivity(intent)
            }
        coinRecyclerAdapter.watchListClickListener =
            CoinRecyclerAdapter.ToggleWatchlistClickListener { item, position ->
                sharedViewModel.toggleWatchlist(item.id)
                val added: Boolean = coinRecyclerAdapter.toggleWatchList(item, position)
                nextWatchListClickHandler?.invoke(item,position, added)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            sharedViewModel.stateFlow.collect { uiState ->
                handleUiState(uiState,binding)
            }
        }

        binding.recyclerWordListView.layoutManager = LinearLayoutManager(context)
        binding.recyclerWordListView.adapter = coinRecyclerAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            onRefreshRequest()
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.let {
            inflater.inflate(R.menu.main_toolbar_menu, menu)
            val searchManager = it.getSystemService(Context.SEARCH_SERVICE) as SearchManager
            (menu.findItem(R.id.search).actionView as SearchView).apply {
                setSearchableInfo(searchManager.getSearchableInfo(it.componentName))
                isSubmitButtonEnabled = true
                isIconifiedByDefault = false
                setOnCloseListener {
                    onRefreshRequest()
                    true
                }
            }

            menu.findItem(R.id.vs_currency).apply {
                title = sharedViewModel.vsCurrency
            }
        }
    }

    override fun onStart() {
        super.onStart()
        onRefreshRequest()
        Intent(context, CoinPriceService::class.java).also { intent ->
            context?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        context?.unbindService(connection)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.vs_currency -> {
                sharedViewModel.vsCurrencies.observe(this) {
                    when(it) {
                        is Resource.Success -> showVsCurrencyDialog(it.result) { newVsCurrency ->
                            item.title = newVsCurrency
                            sharedViewModel.changeVsCurrency(newVsCurrency)
                            onRefreshRequest()
                        }
                        is Resource.Failure ->
                            Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                        else -> {}
                    }
                }
                sharedViewModel.fetchVsCurrencies()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected abstract fun onRefreshRequest()

    private fun showVsCurrencyDialog(vsCurrencies: List<String>, onVsCurrencySelected: (String)->Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.choose_vs_currency))
        builder.setItems(vsCurrencies.toTypedArray()) { dialog, which ->
            onVsCurrencySelected.invoke(vsCurrencies[which])
            dialog.dismiss()
        }
        builder.show()
    }

    private fun handleUiState(uiState: CoinsState, binding: FragmentListBinding) {
        //handle coin state
        binding.swipeRefreshLayout.isRefreshing = uiState.coins is Resource.Loading
        if (uiState.coins is Resource.Success){
            uiState.coins.result?.let {
                coinRecyclerAdapter.changeDataSet(ArrayList(it))
                binding.textViewNothingToShow.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
            }
            coinRecyclerAdapter.watchlistIds = uiState.watchListIds
        }
        else if (uiState.coins is Resource.Failure) {
            Toast.makeText(context, uiState.coins.exception.message, Toast.LENGTH_SHORT).show()
        }

        //handle watchlist track
        if(uiState.watchListIds.size > 0) context?.startForegroundService(Intent(context,CoinPriceService::class.java))
    }

    private val priceChangeListener = CoinPriceService.ServicePriceChangeListener { ids, prices ->
        ids.forEach { id->
            prices.getPrice(id)?.let {
                coinRecyclerAdapter.updatePrice(id, it)
            }
        }
    }

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as CoinPriceService.CoinPriceServiceBinder
            coinPriceService = binder.getService()
            coinPriceService.priceChangeListener = priceChangeListener
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            coinPriceService.priceChangeListener = null
        }
    }
}