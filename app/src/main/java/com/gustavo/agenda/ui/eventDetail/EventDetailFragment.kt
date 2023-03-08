package com.gustavo.agenda.ui.eventDetail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gustavo.agenda.R
import com.gustavo.agenda.databinding.EventDetailFragmentBinding
import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.ui.eventdate.presentation.EventDateFragment.Companion.AGENDA_EVENT_KEY

class EventDetailFragment : Fragment(R.layout.event_detail_fragment) {

    private lateinit var binding: EventDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventDetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrieveArguments()
    }

    private fun retrieveArguments() {
        val agendaEvent: AgendaEvent? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(AGENDA_EVENT_KEY, AgendaEvent::class.java)
        } else {
            arguments?.getParcelable(AGENDA_EVENT_KEY)
        }

        agendaEvent?.let {
            setupAgendaEvent(agendaEvent)
        } ?: run {
            handleErrorOnRetrieveArgs()
        }
    }

    private fun handleErrorOnRetrieveArgs() {

    }

    private fun setupAgendaEvent(agendaEvent: AgendaEvent) {
        with(binding) {
            agendaEventTitle.text = agendaEvent.eventDetail.name
            agendaEventDescription.text = agendaEvent.eventDetail.description
            agendaEventDate.text = agendaEvent.eventDate.toString() + " as " + agendaEvent.eventTime.toString()
        }
    }

}