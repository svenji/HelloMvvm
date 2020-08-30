package com.sven.mvvm.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.jakewharton.rxrelay2.PublishRelay
import com.sven.mvvm.view.InteractionRequest
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class ViewModel : BaseObservable(), LifecycleObserver {
    protected val disposables = CompositeDisposable()

    private val interactionRequestRelay = PublishRelay.create<InteractionRequest>()

    private var interactionRequestSubscription: Disposable? = null

    private lateinit var propertyChangedCallback: Observable.OnPropertyChangedCallback

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    abstract fun bind()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun unbind() {
        disposables.clear()
    }

    fun observe(observer: ViewModelObserver) {
        // Interaction Requests
        interactionRequestSubscription = interactionRequestRelay.subscribe({ request -> observer.onInteractionRequest(request) }, {
            Timber.e(it)
        })

        // Property Observer
        propertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                observer.onPropertyChanged(propertyId)
            }
        }

        // Data Binding
        addOnPropertyChangedCallback(propertyChangedCallback)
    }

    fun clearObserver() {
        interactionRequestSubscription?.dispose()

        // Data Binding
        removeOnPropertyChangedCallback(propertyChangedCallback)
    }

    protected fun requestInteraction(interactionRequest: InteractionRequest) {
        interactionRequestRelay.accept(interactionRequest)
    }
}