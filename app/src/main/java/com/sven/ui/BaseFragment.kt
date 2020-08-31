package com.sven.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.sven.mvvm.view.InteractionRequest
import com.sven.mvvm.viewmodel.BaseViewModel
import com.sven.mvvm.viewmodel.ViewModelObserver
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : HasSupportFragmentInjector,
        ViewModelObserver,
        LifecycleObserver,
        Fragment() {
    @Inject protected lateinit var viewModel: V
    @Inject internal lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    protected lateinit var binding: T

    override fun supportFragmentInjector() = childFragmentInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(this)
        lifecycle.addObserver(viewModel)
        viewModel.observe(this)
        onInitViewModel(savedInstanceState)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun clearViewModelObserver() {
        viewModel.clearObserver()
    }

    protected open fun onInitViewModel(savedInstanceState: Bundle?) {}

    override fun onInteractionRequest(interactionRequest: InteractionRequest) {
        // 1) Fall back to the parent Activity, if it's a BaseActivity
        // 2) Fall back to logging unhandled requests
        when (activity) {
            is BaseActivity<*, *> -> (activity as BaseActivity<*, *>).onInteractionRequest(interactionRequest)
            else -> Timber.d("No handler to handler InteractionRequest: $interactionRequest")
        }
    }

    override fun onPropertyChanged(propertyId: Int) {
        onViewModelPropertyChange(propertyId)
    }

    protected fun observeViewModel(viewModel: BaseViewModel) {
        viewModel.observe(this)
    }

    protected fun clearViewModelObserver(viewModel: BaseViewModel) {
        viewModel.clearObserver()
    }

    /**
     * Called when a ViewModel property changes. Will never be called
     * without first calling [.observeViewModel].
     *
     * @param propertyId the ViewModel's [BaseObservable] property propertyId
     */
    protected open fun onViewModelPropertyChange(propertyId: Int) {}
}
