package com.grizzlyorange.writernotes.ui.data.errors

import com.grizzlyorange.writernotes.R

class Errors {
    companion object {
        val errorsMessagesMap = mapOf<ErrorType, Int>(
            ErrorType.EMPTY_NOTE_NAME_OR_TEXT to R.string.msgEmptyNoteNameOrText
        )
    }

    enum class ErrorType {
        EMPTY_NOTE_NAME_OR_TEXT
    }
}