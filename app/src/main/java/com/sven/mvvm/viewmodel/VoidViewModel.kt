package com.sven.mvvm.viewmodel

import javax.inject.Inject

/**
 * A ViewModel that does nothing. Useful for super simple screens and Activity containers.
 */
class VoidViewModel @Inject constructor() : BaseViewModel() {
    override fun bind() {}
}
