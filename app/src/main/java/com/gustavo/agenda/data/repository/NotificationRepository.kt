package com.gustavo.agenda.data.repository

import com.gustavo.agenda.domain.model.AgendaEvent

interface NotificationRepository {
    fun scheduleEvent(agendaEvent: AgendaEvent): Boolean
}