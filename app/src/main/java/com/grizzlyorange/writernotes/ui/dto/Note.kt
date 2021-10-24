package com.grizzlyorange.writernotes.ui.dto

import java.text.SimpleDateFormat
import java.util.*

data class Note(
    val name: String,
    val text: String,
    private val createDateInMs: Long,
    private val updateDateInMs: Long
) {
    val createDate: String get() = getDateStr(createDateInMs)
    val updateDate: String get() = getDateStr(updateDateInMs)

    private fun getDateStr(timeInMs: Long): String {
        return SimpleDateFormat
            .getDateInstance()
            .format(
                Calendar.getInstance().apply {
                    timeInMillis = createDateInMs
                }
            )
    }
}