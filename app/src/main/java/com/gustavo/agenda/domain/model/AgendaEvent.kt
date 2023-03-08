package com.gustavo.agenda.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AgendaEvent(
    val eventDate: EventDate,
    val eventTime: EventTime,
    val eventDetail: EventDetail
): Parcelable
