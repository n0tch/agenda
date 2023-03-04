package com.gustavo.agenda.event.model

data class EventDetail(
    val name: String,
    val detail: String,
    val timeInMillis: Long
)