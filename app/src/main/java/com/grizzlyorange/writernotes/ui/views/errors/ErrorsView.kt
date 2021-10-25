package com.grizzlyorange.writernotes.ui.views.errors

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.grizzlyorange.writernotes.databinding.ErrorsViewBinding
import com.grizzlyorange.writernotes.ui.data.errors.ErrorsStorage
import com.grizzlyorange.writernotes.ui.views.utils.ViewsUtils

class ErrorsView(
    context: Context,
    attributeSet: AttributeSet
) : FrameLayout(context, attributeSet) {

    private val binding = ErrorsViewBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        val lifeCycleOwner = ViewsUtils.getLifeCycleOwner(this)
        binding.lifecycleOwner = lifeCycleOwner
    }

    fun setErrorsStorage(errorsStorage: ErrorsStorage) {
        binding.errorsStorage = errorsStorage
    }
}