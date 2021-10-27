package com.grizzlyorange.writernotes.ui.customviews.utils

import android.content.ContextWrapper
import android.view.View
import androidx.lifecycle.LifecycleOwner

class ViewsUtils {
    companion object {
        fun getLifeCycleOwner(view: View): LifecycleOwner? {
            var context = view.context
            while (context is ContextWrapper) {
                if (context is LifecycleOwner) {
                    return context
                }
                context = context.baseContext
            }
            return null
        }
    }
}