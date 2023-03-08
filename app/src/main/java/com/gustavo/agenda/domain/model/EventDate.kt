package com.gustavo.agenda.domain.model

import android.icu.text.SimpleDateFormat
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class EventDate(
    val year: Int,
    val month: Int,
    val day: Int
) : Parcelable {
    override fun toString(): String {
        val calendar = Calendar.getInstance().apply {
            set(year, month, day)
        }
        return SimpleDateFormat("d/W/y", Locale.getDefault()).format(calendar.time)
    }
}
