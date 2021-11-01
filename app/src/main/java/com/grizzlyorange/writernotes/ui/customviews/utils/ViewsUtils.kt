package com.grizzlyorange.writernotes.ui.customviews.utils

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

        fun getActivity(view: View, curContext: Context): AppCompatActivity? {
            var context = curContext
            while (context is ContextWrapper) {
                if (context is AppCompatActivity) {
                    return context
                }
                context = context.baseContext
            }
            return null
        }
    }
}