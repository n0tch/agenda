package com.gustavo.agenda.common.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.data.transformation.getDateInMillis
import com.gustavo.agenda.domain.model.EventDate

class AgendaPreferences(
    private val context: Context,
    private val gson: Gson
) {

    private fun getPreferences() =
        context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)

    fun saveEvent(agendaEvent: AgendaEvent) = with(getPreferences().edit()) {
        val savedEvents = getEventsByDay(agendaEvent.eventDate)
            .toMutableList()
            .apply { add(agendaEvent) }

        val agendaEventJson: String = gson.toJson(savedEvents)
        putString(getPreferenceKey(agendaEvent.eventDate), agendaEventJson)
        apply()
    }

    fun getEventsByDay(eventDate: EventDate): List<AgendaEvent> = try {
        val storedAgendaEvent = getPreferences().getString(getPreferenceKey(eventDate), "")

        val type = object : TypeToken<List<AgendaEvent>>() {}.type
        gson.fromJson<List<AgendaEvent>?>(storedAgendaEvent, type)
            .sortedByDescending { it.getDateInMillis() }
    } catch (exception: Exception) {
        emptyList()
    }

    fun getPreferenceKey(eventDate: EventDate): String {
        return AGENDA_EVENT_KEY + listOf(
            eventDate.year,
            eventDate.month,
            eventDate.day
        ).joinToString(separator = "_", prefix = "_")
    }

    companion object {
        private const val PREFERENCE_FILE_NAME = "agenda_pref_file"
        private const val AGENDA_EVENT_KEY = "AGENDA_EVENT_KEY"
    }
}