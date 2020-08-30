package com.sven.mvvm.interactions

import androidx.annotation.StringRes
import com.sven.mvvm.view.InteractionRequest

data class SendEmailInteractionRequest(
        @StringRes val chooserTitleResId: Int,
        @StringRes val targetEmailResId: Int,
        @StringRes val subjectResId: Int,
        @StringRes val bodyResId: Int,
        val subjectFormatArgs: List<Any?>? = null,
        val bodyFormatArgs: List<Any?>? = null) : InteractionRequest {
    fun withSubjectArgs(vararg formatArgs: Any?) = copy(subjectFormatArgs = listOf(*formatArgs))
    fun withBodyArgs(vararg formatArgs: Any?) = copy(bodyFormatArgs = listOf(*formatArgs))
}