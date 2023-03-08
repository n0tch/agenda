package com.gustavo.agenda.domain.model

import android.icu.text.SimpleDateFormat
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Parcelize
data class EventTime(
    val hour: Int,
    val minute: Int,
    val second: Int
): Parcelable {
    override fun toString(): String {
        val localTime = LocalTime.of(hour , minute)
        return localTime.format(DateTimeFormatter.ofPattern("H:mm"))
//        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
//        return simpleDateFormat.format(localTime)
    }
}
