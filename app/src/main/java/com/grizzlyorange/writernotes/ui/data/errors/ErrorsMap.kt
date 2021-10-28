package com.grizzlyorange.writernotes.ui.data.errors

import com.grizzlyorange.writernotes.R
import com.grizzlyorange.writernotes.domain.DomainErrors
import com.grizzlyorange.writernotes.domain.models.Note

class ErrorsMap {
    companion object {
        val domainErrorsToMessagesId = mapOf<DomainErrors, Int>(
            DomainErrors.EMPTY_NOTE_NAME_OR_TEXT to R.string.msgEmptyNoteNameOrText
        )

    }
}