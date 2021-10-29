package com.grizzlyorange.writernotes.ui.utils.formatters

import java.text.SimpleDateFormat

class DateTimeFormatter {
    companion object {
        fun getDateStr(timeInMs: Long): String {
            return SimpleDateFormat
                .getDateInstance()
                .format(timeInMs)
        }

        fun getDateTimeStr(timeInMs: Long): String {
            return SimpleDateFormat
                .getDateTimeInstance()
                .format(timeInMs)
        }
    }
}