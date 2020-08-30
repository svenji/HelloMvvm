package com.sven.mvvm.view

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.sven.mvvm.interactions.DismissKeyboardInteractionRequest
import com.sven.mvvm.interactions.LogInteractionRequest
import com.sven.mvvm.interactions.OpenUrlInteractionRequest
import com.sven.mvvm.interactions.ShowToastInteractionRequest
import com.sven.ui.BaseActivity
import com.sven.util.dismissKeyboard
import timber.log.Timber
import java.lang.ref.WeakReference

class DefaultInteractionRequestHandler(activity: BaseActivity<*, *>) : InteractionRequestHandler {
    private val activity: WeakReference<BaseActivity<*, *>> = WeakReference(activity)

    override fun onInteractionRequest(interactionRequest: InteractionRequest) {
        when (interactionRequest) {
            is LogInteractionRequest -> handleLogInteractionRequest(interactionRequest)
            is ShowToastInteractionRequest -> handleShowToastInteractionRequest(interactionRequest)
            is OpenUrlInteractionRequest -> handleOpenUrlInteractionRequest(interactionRequest)
            is DismissKeyboardInteractionRequest -> handleDismissKeyboardInteractionRequest()
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

    private fun handleShowToastInteractionRequest(request: ShowToastInteractionRequest) {
        val activity = activity.get()
        if (activity != null) {
            val messageResourceId: Int = request.messageResourceId
            val args: List<Any?>? = request.messageArgs
            if (args == null || args.isEmpty()) {
                Toast.makeText(activity, messageResourceId, if (request.isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                        activity, activity.getString(messageResourceId, *args.toTypedArray()),
                        if (request.isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun handleOpenUrlInteractionRequest(request: OpenUrlInteractionRequest) {
        val activity = activity.get()
        if (activity != null) {
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(activity, Uri.parse(request.route))
        }
    }

    private fun handleDismissKeyboardInteractionRequest() {
        this.activity.get()?.dismissKeyboard()
    }
}