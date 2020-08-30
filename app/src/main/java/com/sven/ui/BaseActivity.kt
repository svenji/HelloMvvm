package com.sven.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.sven.mvvm.view.DefaultInteractionRequestHandler
import com.sven.mvvm.view.InteractionRequest
import com.sven.mvvm.view.InteractionRequestHandler
import com.sven.mvvm.viewmodel.BaseViewModel
import com.sven.mvvm.viewmodel.ViewModelObserver
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : ViewModelObserver, LifecycleObserver, DaggerAppCompatActivity() {
    @Inject lateinit var viewModel: V
    lateinit var binding: T

    private lateinit var interactionRequestHandler: InteractionRequestHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBindView()
        lifecycle.addObserver(this)
        lifecycle.addObserver(viewModel)
        viewModel.observe(this)
        onInitViewModel(savedInstanceState)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun bindInteractionHandler() {
        interactionRequestHandler = DefaultInteractionRequestHandler(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unbind() {
        viewModel.clearObserver()
    }

    abstract fun onBindView()

    open fun onInitViewModel(savedInstanceState: Bundle?) {}

    /**
     * Called when the ViewModel publishes a new InteractionRequest. Will never be called
     * without first calling [.observeViewModel].
     *
     * @param interactionRequest the InteractionRequest
     */
    override fun onInteractionRequest(interactionRequest: InteractionRequest) {
        interactionRequestHandler.onInteractionRequest(interactionRequest)
    }

    override fun onPropertyChanged(propertyId: Int) {
        onViewModelPropertyChanged(propertyId)
    }

    protected open fun onViewModelPropertyChanged(propertyId: Int) {}
}