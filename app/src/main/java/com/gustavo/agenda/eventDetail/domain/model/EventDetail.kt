package com.gustavo.agenda.eventDetail.domain.model

data class EventDetail(
    val name: String,
    val detail: String,
    val timeInMillis: Long
)