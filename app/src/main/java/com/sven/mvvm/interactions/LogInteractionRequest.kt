package com.sven.mvvm.interactions

import com.sven.mvvm.view.InteractionRequest

data class LogInteractionRequest(val priority: Int, val tag: String, val message: String?, val throwable: Throwable?) : InteractionRequest {
    companion object {
        // These constants are pulled straight from android.util.Log
        private const val ASSERT = 7
        private const val DEBUG = 3
        private const val ERROR = 6
        private const val INFO = 4
        private const val VERBOSE = 2
        private const val WARN = 5

        fun v(tag: String, msg: String) = LogInteractionRequest(VERBOSE, tag, msg, null)
        fun d(tag: String, msg: String) = LogInteractionRequest(DEBUG, tag, msg, null)
        fun i(tag: String, msg: String) = LogInteractionRequest(INFO, tag, msg, null)
        fun w(tag: String, msg: String) = LogInteractionRequest(WARN, tag, msg, null)
        fun w(tag: String, tr: Throwable) = LogInteractionRequest(WARN, tag, null, tr)
        fun e(tag: String, msg: String) = LogInteractionRequest(ERROR, tag, msg, null)

        fun v(tag: String, msg: String, tr: Throwable) = LogInteractionRequest(VERBOSE, tag, msg, tr)
        fun d(tag: String, msg: String, tr: Throwable) = LogInteractionRequest(DEBUG, tag, msg, tr)
        fun i(tag: String, msg: String, tr: Throwable) = LogInteractionRequest(INFO, tag, msg, tr)
        fun w(tag: String, msg: String, tr: Throwable) = LogInteractionRequest(WARN, tag, msg, tr)
        fun e(tag: String, msg: String?, tr: Throwable) = LogInteractionRequest(ERROR, tag, msg, tr)

        // TODO support .wtf(...) variants (harder because there's no constant for them)?

        fun println(priority: Int, tag: String, msg: String) = LogInteractionRequest(priority, tag, msg, null)
    }
}