package nferno1.cfreddit.domain

import java.text.SimpleDateFormat
import java.util.*

fun Int.formatDecimalSeparator(): String {
    return toString()
        .reversed()
        .chunked(3)
        .joinToString(" ")
        .reversed()
}

fun Long.toDateTimeFormat(): String {
    return SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date(this))
}