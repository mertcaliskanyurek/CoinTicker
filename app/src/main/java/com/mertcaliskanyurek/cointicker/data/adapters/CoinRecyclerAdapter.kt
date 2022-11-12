package com.mertcaliskanyurek.cointicker.data.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.Coin
import com.mertcaliskanyurek.cointicker.databinding.LayoutCoinListItemBinding

class CoinRecyclerAdapter(
    coinList: ArrayList<Coin>
) : GenericRecyclerAdapter<Coin>(coinList) {

    companion object {
        const val TAG = "CoinRecyclerAdapter"
    }

    var watchListClickListener: ToggleWatchlistClickListener? = null
    var watchlistIds: ArrayList<String> = ArrayList()
        set(value) {
            field = value
            updateWatchListData()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Coin> {
        val binding = LayoutCoinListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return object : BaseViewHolder<Coin>(binding){}
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Coin>, position: Int) {
        val binding = holder.dataBinding as LayoutCoinListItemBinding
        binding.coin = dataList[position]
        binding.isInWatchlist = watchlistIds.indexOf(dataList[position].id) > -1
        Glide.with(holder.itemView.context).load(dataList[position].image).into(binding.ivCoinImage)
        itemClickListener?.let { listener ->
            binding.root.setOnClickListener { listener.onItemClick(dataList[position],position) }
        }
        watchListClickListener?.let { listener ->
            binding.ivWatchList.setOnClickListener { listener.onToggleToWatchlistClick(dataList[position],position) }
        }
    }

    /**
     * @return true if added to watchlist false otherwise
     */
    fun toggleWatchList(coin: Coin,position: Int): Boolean = watchlistIds.indexOf(coin.id).let { index ->
        if(index > -1) watchlistIds.removeAt(index)
        else watchlistIds.add(coin.id)
        notifyItemChanged(position)

        return index < 0
    }

    fun updatePrice(coinId: String, price: Double) {
        val positionOfDataList =
            dataList.withIndex().first { item -> item.value.id == coinId }.index
        dataList[positionOfDataList].current_price = price
        notifyItemChanged(positionOfDataList)
    }

    private fun updateWatchListData() {
        try {
            watchlistIds.forEach { coinId ->
                val positionOfDataList =
                    dataList.withIndex().first { item -> item.value.id == coinId }.index
                notifyItemChanged(positionOfDataList)
            }
        }catch (e: NoSuchElementException) {
            Log.e(TAG, "WatchList id not found in data list. DataList might not be initialized")
        }
    }

    fun interface ToggleWatchlistClickListener {
        fun onToggleToWatchlistClick(item:Coin, position: Int)
    }
}