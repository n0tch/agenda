package com.gustavo.agenda.presentation.event.model

data class EventDetail(
    val name: String,
    val detail: String,
    val timeInMillis: Long
)