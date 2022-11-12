package com.mertcaliskanyurek.cointicker.ui.feature.coin_detail

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
class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<CoinDetailState>(CoinDetailState(Resource.Loading,false))
    val stateFlow: StateFlow<CoinDetailState> = _stateFlow

    fun fetchCoinDetail(id: String) = viewModelScope.launch(Dispatchers.IO) {
        val detailResult = coinRepository.getCoinDetail(id)
        val isInWatchList = coinRepository.isInWatchList(id)
        _stateFlow.value = CoinDetailState(detailResult, isInWatchList)
    }

    fun toggleWatchList(id:String) = viewModelScope.launch(Dispatchers.IO) {
        coinRepository.toggleWatchList(id)
        _stateFlow.value = CoinDetailState(_stateFlow.value.coinDetails, !_stateFlow.value.watchList)
    }
}