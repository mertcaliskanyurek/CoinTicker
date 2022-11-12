package com.mertcaliskanyurek.cointicker.data.providers

import android.content.SearchRecentSuggestionsProvider

class CoinSuggestionProvider : SearchRecentSuggestionsProvider() {

    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.loodos.bitcointicker.CoinSuggestionProvider"
        const val MODE: Int = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
    }

}