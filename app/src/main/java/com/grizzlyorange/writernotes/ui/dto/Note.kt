package com.grizzlyorange.writernotes.ui.dto

import java.text.SimpleDateFormat

data class Note(
    val noteId: Long = 0,
    val name: String = "",
    val text: String = "",
    private val createDateInMs: Long = 0,
    private val updateDateInMs: Long = 0
) {
    val createDate: String get() = getDateStr(createDateInMs)
    val updateDate: String get() = getDateStr(updateDateInMs)

    private fun getDateStr(timeInMs: Long): String {
        return SimpleDateFormat
            .getDateInstance()
            .format(timeInMs)
    }
}