package com.sven.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.sven.R

fun Activity.dismissKeyboard() {
    if (isFinishing) {
        return
    }
    val focus = currentFocus
    if (focus != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(focus.windowToken, 0)
    }
}

fun Context.dismissKeyboardFrom(view: View?) {
    if (null == view) {
        return
    }

    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showKeyboard(v: View?) {
    if (isFinishing) {
        return
    }

    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.showKeyboardForced(v: View?) {
    if (v == null) {
        return
    }

    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun Activity.openUrl(route: String) {
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(this, Uri.parse(route))
}

/**
 * Starts an intent to send an email. Allows sending by any app which supports [Intent.ACTION_SEND]
 * with MIME type "text/plain",
 *
 * @param activity
 * @param subject
 * @param body
 */
fun Activity?.startEmailIntent(chooserTitle: String?, toEmailAddress: String, subject: String?, body: String?) {
    if (null == this || isFinishing) {
        return
    }
    val emailIntent: Intent
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val selectorIntent = Intent(Intent.ACTION_SENDTO)
            selectorIntent.data = Uri.parse("mailto:")
            emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(toEmailAddress))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, body)
            emailIntent.selector = selectorIntent
            startActivity(Intent.createChooser(emailIntent, chooserTitle))
        } else {
            emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", toEmailAddress, null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, body)
        }
        startActivity(Intent.createChooser(emailIntent, chooserTitle))
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(this, R.string.no_email_client_error, Toast.LENGTH_SHORT).show()
    }
}

fun Activity.copyTextToClipboard(label: String?, text: String?) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
}