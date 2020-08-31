package com.sven.mvvm.view

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.sven.R
import com.sven.mvvm.interactions.CopyTextToClipboardInteractionRequest
import com.sven.mvvm.interactions.DismissKeyboardInteractionRequest
import com.sven.mvvm.interactions.LogInteractionRequest
import com.sven.mvvm.interactions.OpenUrlInteractionRequest
import com.sven.mvvm.interactions.SendEmailInteractionRequest
import com.sven.mvvm.interactions.ShowToastInteractionRequest
import com.sven.ui.BaseActivity
import com.sven.util.copyTextToClipboard
import com.sven.util.dismissKeyboard
import com.sven.util.openUrl
import com.sven.util.startEmailIntent
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
            is SendEmailInteractionRequest -> handleSendEmailInteractionRequest(interactionRequest)
            is CopyTextToClipboardInteractionRequest -> handleCopyToClipboardInteractionRequest(interactionRequest)
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
        val activity = activity.get() ?: return
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

    private fun handleOpenUrlInteractionRequest(request: OpenUrlInteractionRequest) {
        activity.get()?.openUrl(request.route)
    }

    private fun handleDismissKeyboardInteractionRequest() {
        this.activity.get()?.dismissKeyboard()
    }

    private fun handleSendEmailInteractionRequest(request: SendEmailInteractionRequest) {
        val activity: Activity = activity.get() ?: return
        val r = activity.resources
        val chooserTitle: String
        val toEmailAddress: String
        val emailSubject: String
        val emailBody: String

        // Chooser Title
        chooserTitle = if (request.chooserTitleResId != 0) {
            r.getString(request.chooserTitleResId)
        } else {
            r.getString(R.string.send_email_default_chooser_title)
        }

        // To:
        toEmailAddress = if (request.targetEmailResId != 0) {
            r.getString(request.targetEmailResId)
        } else {
            ""
        }

        // Subject
        emailSubject = if (request.targetEmailResId != 0) {
            r.getString(request.targetEmailResId, request.subjectFormatArgs)
        } else {
            ""
        }

        // Body
        emailBody = if (request.bodyResId != 0) {
            r.getString(request.bodyResId, request.bodyFormatArgs)
        } else {
            ""
        }

        activity.startEmailIntent(chooserTitle, toEmailAddress, emailSubject, emailBody)
    }

    private fun handleCopyToClipboardInteractionRequest(request: CopyTextToClipboardInteractionRequest) {
        activity.get()?.copyTextToClipboard(request.label, request.text)
    }
}