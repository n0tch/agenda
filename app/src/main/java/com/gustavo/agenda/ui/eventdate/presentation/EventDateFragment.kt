package com.gustavo.agenda.ui.eventdate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gustavo.agenda.R
import com.gustavo.agenda.common.ui.defaultAlert
import com.gustavo.agenda.databinding.EventDateFragmentBinding
import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.ui.eventdate.adapter.AgendaEventAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDateFragment : Fragment(R.layout.event_date_fragment) {

    private lateinit var binding: EventDateFragmentBinding
    private val viewModel by viewModel<EventDateViewModel>()
    private val agendaEventAdapter by lazy {
        AgendaEventAdapter { handleEventClicked(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventDateFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        setupClickListeners()
        setupDatePicker()
        setupRecyclerView()

        viewModel.getEvents(
            binding.datePickerView.year,
            binding.datePickerView.month,
            binding.datePickerView.dayOfMonth
        )
    }

    private fun setupRecyclerView() {
        binding.agendaEventsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.agendaEventsRecyclerView.adapter = agendaEventAdapter
    }

    private fun setupDatePicker() {
        viewModel.updateSelectedDate(
            binding.datePickerView.year,
            binding.datePickerView.month,
            binding.datePickerView.dayOfMonth,
        )
    }

    private fun setupObservables() {
        viewModel.getEventDateState().observe(viewLifecycleOwner) {
            handleEventDateState(it)
        }
    }

    private fun handleEventDateState(eventState: EventDateState) = when (eventState) {
        is EventDateState.DateSelected -> updateTitle(
            eventState.year,
            eventState.month,
            eventState.day
        )
        is EventDateState.AgendaEvents -> {
            handleAgendaEvents(eventState.agendaEvents)
        }
        is EventDateState.ErrorLoadingAgendaEvents -> {}
    }

    private fun handleAgendaEvents(agendaEvents: List<AgendaEvent>) {
        agendaEventAdapter.update(agendaEvents)
    }

    private fun updateTitle(year: Int, month: Int, day: Int) {
        binding.eventListTextView.text = "$day/$month/$year"
    }

    private fun setupClickListeners() {
        val date = viewModel.getCurrentDate()
        binding.datePickerView.init(date.year, date.month, date.day) { _, year, month, day ->
            viewModel.updateSelectedDate(year, month, day)
            viewModel.getEvents(year, month, day)
        }

        binding.addEventButton.setOnClickListener {
            requireContext().defaultAlert(
                title = "Adicionar evento",
                description = "Adicionar notificação para o dia ${binding.datePickerView.dayOfMonth}/${binding.datePickerView.month}/${binding.datePickerView.year}?",
                positiveButtonText = "Avançar",
                positiveButtonAction = {
                    navigateToEventFragment()
                },
                negativeButtonText = "Cancelar",
                negativeButtonAction = {}
            )

        }
    }

    private fun createDateBundle() = Bundle().apply {
        putInt(DATE_DAY_KEY, binding.datePickerView.dayOfMonth)
        putInt(DATE_MONTH_KEY, binding.datePickerView.month)
        putInt(DATE_YEAR_KEY, binding.datePickerView.year)
    }

    private fun createAgendaEventBundle(agendaEvent: AgendaEvent) = Bundle().apply {
        putParcelable(AGENDA_EVENT_KEY, agendaEvent)
    }

    private fun navigateToEventFragment() {
        findNavController().navigate(
            R.id.action_agendaFragment_to_eventConfigFragment,
            createDateBundle()
        )
    }

    private fun handleEventClicked(agendaEvent: AgendaEvent) {
        findNavController().navigate(
            R.id.action_agendaFragment_to_eventDetail,
            createAgendaEventBundle(agendaEvent)
        )
    }

    companion object {
        const val DATE_DAY_KEY = "date_day"
        const val DATE_MONTH_KEY = "date_month"
        const val DATE_YEAR_KEY = "date_year"
        const val AGENDA_EVENT_KEY = "agenda_event"
    }
}