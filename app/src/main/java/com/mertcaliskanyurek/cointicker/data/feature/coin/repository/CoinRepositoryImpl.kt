package com.mertcaliskanyurek.cointicker.data.feature.coin.repository

import android.content.Context
import com.mertcaliskanyurek.cointicker.data.Resource
import com.mertcaliskanyurek.cointicker.data.feature.coin.dao.CoinDAO
import com.mertcaliskanyurek.cointicker.data.feature.coin.dao.CoinWatchlistDAO
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.Coin
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.CoinDetail
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.CoinPricesWrapper
import com.mertcaliskanyurek.cointicker.data.feature.coin.model.CoinWatchlist
import com.mertcaliskanyurek.cointicker.data.network.CoingeckoApi
import org.json.JSONObject
import java.util.Calendar
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val context: Context,
    private val api: CoingeckoApi,
    private val coinDAO: CoinDAO,
    private val coinWatchlistDAO: CoinWatchlistDAO
): CoinRepository {

    companion object {
        const val PREF_NAME = "CoinPreferences"
        const val KEY_VS_CURRENCY = "VS_CURRENCY"
        const val VS_CURRENCY_DEF_VAL = "usd"
    }

    override var vsCurrency: String = VS_CURRENCY_DEF_VAL
        get() {
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getString(KEY_VS_CURRENCY,
                VS_CURRENCY_DEF_VAL)?.apply {
                    return this
            }
            return field
        }
        set(value){
            field = value
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putString(
                KEY_VS_CURRENCY,value).commit()
        }

    override fun getCoin(id: String): Coin? = try {
        coinDAO.getCoin(id)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    override fun getWatchListCoinIds(): List<String> = try {
        coinWatchlistDAO.getWatchListCoinIds()
    } catch (e: Exception) {
        e.printStackTrace()
        ArrayList()
    }

    override fun getWatchListCoins(watchListIds: List<String>): List<Coin> = try {
        coinWatchlistDAO.getWatchListCoins(watchListIds)
    } catch (e: Exception) {
        e.printStackTrace()
        ArrayList()
    }

    override suspend fun getCoins(): Resource<List<Coin>> = try {
        val result = api.coinList(vsCurrency)
        if (result.isSuccessful) {
            coinDAO.insert(result.body()!!)
            Resource.Success(result.body()!!)
        }
        else {
            val errBody = JSONObject(result.errorBody()!!.string()).getJSONObject("status").getString("error_message")
            Resource.Failure(Exception(errBody))
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.Failure(e)
    }

    override fun isInWatchList(id: String): Boolean = try {
        val result = coinWatchlistDAO.count(id)
        result > 0
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    override fun searchCoin(filter: String): Resource<List<Coin>?> = try {
        val result = coinDAO.search(filter.lowercase())
        Resource.Success(result)
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.Failure(e)
    }

    override suspend fun getCoinDetail(id: String): Resource<CoinDetail> = try {
        val result = api.coinDetail(id)
        if (result.isSuccessful) Resource.Success(result.body()!!)
        else {
            val errBody = JSONObject(result.errorBody()!!.string()).getJSONObject("status").getString("error_message")
            Resource.Failure(Exception(errBody))
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.Failure(e)
    }

    override suspend fun getCoinPrices(ids: List<String>): Resource<CoinPricesWrapper> = try {
        val vsCurrency = this.vsCurrency
        val result = api.prices(ids.joinToString(","),vsCurrency)
        if (result.isSuccessful) {
            val wrapper = CoinPricesWrapper()
            ids.forEach { coinId->
                val price = result.body()!![coinId]!![vsCurrency]!!
                wrapper.addPrice(coinId,price)
            }
            Resource.Success(wrapper)
        }
        else {
            val errBody = JSONObject(result.errorBody()!!.string()).getJSONObject("status").getString("error_message")
            Resource.Failure(Exception(errBody))
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.Failure(e)
    }

    override suspend fun getSupportedVsCurrencies(): Resource<List<String>> = try {
        val result = api.supportedVsCurrencies()
        if (result.isSuccessful) {
            Resource.Success(result.body()!!)
        }
        else {
            val errBody = JSONObject(result.errorBody()!!.string()).getJSONObject("status").getString("error_message")
            Resource.Failure(Exception(errBody))
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.Failure(e)
    }

    override suspend fun toggleWatchList(coindId:String){
        if(isInWatchList(coindId)) coinWatchlistDAO.delete(coindId)
        else coinWatchlistDAO.insert(CoinWatchlist(
            coinId = coindId, dateAdded = Calendar.getInstance().time.toString())
        )
    }

    override suspend fun updateCoinPrice(id: String, price: Double) = coinDAO.updatePrice(id, price)

}