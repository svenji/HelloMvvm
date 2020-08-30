package com.sven.mvvm.viewmodel

import com.sven.mvvm.view.InteractionRequest

/**
 * RequestConfirmationListener for observing a [BaseViewModel].
 */
interface ViewModelObserver {
    /**
     * Called when the ViewModel has published an [InteractionRequest].
     *
     * @param request the InteractionRequest to be serviced by the observer.
     */
    fun onInteractionRequest(interactionRequest: InteractionRequest)

    /**
     * Simplified version of [androidx.databinding.Observable.OnPropertyChangedCallback], since
     * we don't need to know the sender (its always the ViewModel) and in order to avoid naming
     * clashes with RxJava Observable.
     *
     * @param propertyId
     */
    fun onPropertyChanged(propertyId: Int)
}
