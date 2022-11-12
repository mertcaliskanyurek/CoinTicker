package com.mertcaliskanyurek.cointicker.data.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:errorText")
fun setError(tInputLayout: TextInputLayout, str: String?) {
    if (str != null && str.isNotEmpty()) {
        tInputLayout.error = str
    }
    else tInputLayout.error = null
}

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}
