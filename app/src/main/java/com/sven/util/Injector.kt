package com.sven.util

import android.content.Context
import com.sven.BaseApplication
import com.sven.dagger.components.BaseComponent

class Injector {
    private fun Injector() {
        throw AssertionError("Do not create instances of this class")
    }

    companion object {
        @SuppressWarnings("ResourceType")  // Using a custom service, so ignore the warning
        fun obtain(context: Context): BaseComponent {
            return context.getSystemService(BaseApplication.INJECTOR_SERVICE) as BaseComponent
        }

        fun matchesService(name: String): Boolean {
            return name == BaseApplication.INJECTOR_SERVICE
        }
    }
}