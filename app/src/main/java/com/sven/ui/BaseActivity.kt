package com.sven.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.sven.mvvm.viewmodel.ViewModel
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, V : ViewModel> : AppCompatActivity() {
    @Inject lateinit var viewModel: V
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBindView()
        onInitViewModel(savedInstanceState)
    }

    abstract fun onBindView()

    open fun onInitViewModel(savedInstanceState: Bundle?) {}

    override fun onStart() {
        super.onStart()
        viewModel.bind()
    }

    override fun onStop() {
        super.onStop()
        viewModel.unbind()
    }
}