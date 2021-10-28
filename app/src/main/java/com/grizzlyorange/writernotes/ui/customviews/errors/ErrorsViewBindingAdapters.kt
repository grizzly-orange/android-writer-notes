package com.grizzlyorange.writernotes.ui.customviews.errors

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.grizzlyorange.writernotes.domain.DomainErrors
import com.grizzlyorange.writernotes.ui.data.errors.ErrorsMap

@BindingAdapter ("errors")
fun displayErrors(txtErrors: TextView, errors: Set<DomainErrors>?) {
    if (errors == null) {
        return
    }
    txtErrors.text = getErrorsText(txtErrors.context, errors)
    txtErrors.visibility = getErrorsVisibility(errors)
}

fun getErrorsText(context: Context, errors: Set<DomainErrors>): String {
    return errors.map { error ->
            ErrorsMap.domainErrorsToMessagesId[error]?.let { messageId ->
                context.getString(messageId) }

            }.joinToString("\n")
}

fun getErrorsVisibility(errors: Set<DomainErrors>): Int {
    return if (errors.isEmpty()) View.GONE else View.VISIBLE
}