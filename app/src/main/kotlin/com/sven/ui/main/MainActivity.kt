package com.sven.ui.main

import android.databinding.DataBindingUtil
import com.sven.R
import com.sven.databinding.MainBinding
import com.sven.ui.BaseActivity
import com.sven.ui.main.viewmodel.MainViewModel
import com.sven.util.Injector

class MainActivity : BaseActivity<MainBinding, MainViewModel>() {
    override fun onInjectDependencies() {
        Injector.obtain(applicationContext).inject(this)
    }

    override fun onBindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.main)
    }
}