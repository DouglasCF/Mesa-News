package br.com.fornaro.mesanews.data.extensions

import br.com.fornaro.mesanews.domain.exceptions.InvalidDateFormatException
import java.text.SimpleDateFormat
import java.util.*

const val REMOTE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

fun String.toCalendar(format: String = REMOTE_DATE_FORMAT): Calendar =
    Calendar.getInstance().also {
        it.time = SimpleDateFormat(format, Locale.ROOT).parse(this)
            ?: throw InvalidDateFormatException()
    }