package com.sven.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.sven.R
import com.sven.databinding.BlankToolbarActivityBinding
import com.sven.mvvm.viewmodel.BaseViewModel
import com.sven.util.setDisplayHomeAsUpEnabled

abstract class BlankToolbarActivity<V : BaseViewModel> : BaseActivity<BlankToolbarActivityBinding, V>() {
    override fun onBindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.blank_toolbar_activity)
        setSupportActionBar(binding.blankToolbarActivityToolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}