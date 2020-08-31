package com.sven.ui.main

import androidx.databinding.DataBindingUtil
import com.sven.R
import com.sven.databinding.MainBinding
import com.sven.ui.BaseActivity
import com.sven.ui.main.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainBinding, MainViewModel>() {
    override fun onBindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.main)
    }
}