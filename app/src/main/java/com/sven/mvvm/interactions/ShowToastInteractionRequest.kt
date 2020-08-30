package com.sven.mvvm.interactions

import com.sven.mvvm.view.InteractionRequest

data class ShowToastInteractionRequest(val isShort: Boolean = true, val messageResourceId: Int, val messageArgs: List<Any?>?) : InteractionRequest {
    constructor(isShort: Boolean = true, messageResourceId: Int, vararg messageArgs: Any?) : this(isShort, messageResourceId, listOf(*messageArgs))
}