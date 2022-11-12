package com.mertcaliskanyurek.cointicker.providers

import androidx.annotation.StringRes

interface ResourceProvider{
    fun getString(@StringRes resId: Int): String
}