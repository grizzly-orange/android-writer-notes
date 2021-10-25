package com.grizzlyorange.writernotes.ui.views.errors

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.grizzlyorange.writernotes.ui.data.errors.Errors

@BindingAdapter ("errors")
fun displayErrors(txtErrors: TextView, errors: Set<Errors.ErrorType>?) {
    if (errors == null) {
        return
    }
    txtErrors.text = getErrorsText(txtErrors.context, errors)
    txtErrors.visibility = getErrorsVisibility(errors)
}

fun getErrorsText(context: Context, errors: Set<Errors.ErrorType>): String {
    return errors.map { errorType ->
        Errors.errorsMessagesMap[errorType]?.let { messageId ->
            context.getString(messageId) }
    }.joinToString ("\n" )
}

fun getErrorsVisibility(errors: Set<Errors.ErrorType>): Int {
    return if (errors.isEmpty()) View.GONE else View.VISIBLE
}