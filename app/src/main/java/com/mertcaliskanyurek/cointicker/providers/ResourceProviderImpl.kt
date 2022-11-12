package com.mertcaliskanyurek.cointicker.providers

import android.content.Context

class ResourceProviderImpl(
    private val context: Context
) : ResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)
}