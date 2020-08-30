package com.sven.mvvm.view

import android.util.Log
import com.sven.mvvm.interactions.LogInteractionRequest
import com.sven.ui.BaseActivity
import timber.log.Timber
import java.lang.ref.WeakReference

class DefaultInteractionRequestHandler(activity: BaseActivity<*, *>) : InteractionRequestHandler {
    private val activity: WeakReference<BaseActivity<*, *>> = WeakReference(activity)

    override fun onInteractionRequest(interactionRequest: InteractionRequest) {
        when (interactionRequest) {
            is LogInteractionRequest -> handleLogInteractionRequest(interactionRequest)
            else -> Timber.d("Unhandled InteractionRequest: $interactionRequest")

        }
    }

    private fun handleLogInteractionRequest(request: LogInteractionRequest) {
        val message = request.message ?: ""
        if (null == request.throwable) {
            Log.println(request.priority, request.tag, message)
        } else {
            Log.println(request.priority, request.tag, "$message\n${Log.getStackTraceString(request.throwable)}")
        }
    }
}