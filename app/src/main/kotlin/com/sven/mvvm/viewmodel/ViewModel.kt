package com.sven.mvvm.viewmodel

import androidx.databinding.BaseObservable
import io.reactivex.disposables.CompositeDisposable

abstract class ViewModel : BaseObservable() {
    protected val mDisposables = CompositeDisposable()

    abstract fun bind()

    fun unbind() {
        mDisposables.clear()
    }
}