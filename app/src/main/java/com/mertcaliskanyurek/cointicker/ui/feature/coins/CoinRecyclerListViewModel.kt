package com.mertcaliskanyurek.cointicker.ui.feature.coins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mertcaliskanyurek.cointicker.data.Resource
import com.mertcaliskanyurek.cointicker.data.feature.coin.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinRecyclerListViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<CoinsState>(
        CoinsState(
            Resource.Loading,
            ArrayList()
        )
    )
    val stateFlow: StateFlow<CoinsState> = _stateFlow

    private val _vsCurrencies: MutableLiveData<Resource<List<String>>> = MutableLiveData(Resource.Loading)
    val vsCurrencies: LiveData<Resource<List<String>>> = _vsCurrencies

    var vsCurrency = coinRepository.vsCurrency

    fun fetchCoins() = viewModelScope.launch(Dispatchers.IO) {
        _stateFlow.value  = CoinsState(Resource.Loading,_stateFlow.value.watchListIds)
        val coinsResult = coinRepository.getCoins()
        val watchListIds = coinRepository.getWatchListCoinIds()
        _stateFlow.value = CoinsState(coinsResult, ArrayList(watchListIds))
    }

    fun fetchWatchList() = viewModelScope.launch(Dispatchers.IO) {
        _stateFlow.value  = CoinsState(Resource.Loading,_stateFlow.value.watchListIds)
        val watchListIds = coinRepository.getWatchListCoinIds()
        val coinsResult = coinRepository.getWatchListCoins(watchListIds)
        _stateFlow.value = CoinsState(Resource.Success(coinsResult), ArrayList(watchListIds))
    }

    fun fetchVsCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        val result = coinRepository.getSupportedVsCurrencies()
        _vsCurrencies.postValue(result)
    }

    fun searchCoins(filter: String) = viewModelScope.launch(Dispatchers.IO) {
        _stateFlow.value  = CoinsState(Resource.Loading,_stateFlow.value.watchListIds)
        val result = coinRepository.searchCoin(filter)
        _stateFlow.value = CoinsState(result,_stateFlow.value.watchListIds)
    }

    fun changeVsCurrency(newVsCurrency: String) {
        coinRepository.vsCurrency = newVsCurrency
        vsCurrency = newVsCurrency
    }

    fun toggleWatchlist(id: String) = viewModelScope.launch(Dispatchers.IO) {
        coinRepository.toggleWatchList(id)
    }

}