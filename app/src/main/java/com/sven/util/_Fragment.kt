package com.sven.util

import android.graphics.drawable.ColorDrawable
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sven.ui.BaseActivity

fun Fragment.showToolbar(show: Boolean) {
    with(activity as? BaseActivity<*, *>) {
        if (show) this?.supportActionBar?.show() else this?.supportActionBar?.hide()
    }
}

fun Fragment.setToolbarColor(@ColorRes colorId: Int) {
    val appCompatActivity = activity as? AppCompatActivity
    val actionBar = appCompatActivity?.supportActionBar
    actionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(colorId, null)))
}