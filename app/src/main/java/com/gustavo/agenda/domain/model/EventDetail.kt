package com.gustavo.agenda.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventDetail(
    val name: String,
    val description: String
): Parcelable