package com.sven.mvvm.interactions

import com.sven.mvvm.view.InteractionRequest

/**
 * A request to open a url.
 */
data class OpenUrlInteractionRequest(val route: String) : InteractionRequest
