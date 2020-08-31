package com.sven.util

import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.setDisplayHomeAsUpEnabled(enabled: Boolean) {
    supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
}

fun AppCompatActivity.showToolbar(show: Boolean) {
    if (show) {
        supportActionBar?.show()
    } else {
        supportActionBar?.hide()
    }
}