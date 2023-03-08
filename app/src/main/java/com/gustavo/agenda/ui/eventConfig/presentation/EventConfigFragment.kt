package com.gustavo.agenda.ui.eventConfig.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.gustavo.agenda.R
import com.gustavo.agenda.domain.model.AgendaEvent
import com.gustavo.agenda.common.ui.defaultAlert
import com.gustavo.agenda.databinding.EventConfigFragmentBinding
import com.gustavo.agenda.domain.model.EventDate
import com.gustavo.agenda.ui.eventdate.presentation.EventDateFragment.Companion.DATE_DAY_KEY
import com.gustavo.agenda.ui.eventdate.presentation.EventDateFragment.Companion.DATE_MONTH_KEY
import com.gustavo.agenda.ui.eventdate.presentation.EventDateFragment.Companion.DATE_YEAR_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventConfigFragment : Fragment(R.layout.event_config_fragment) {

    private lateinit var binding: EventConfigFragmentBinding
    private val navController by lazy { Navigation.findNavController(binding.root) }
    private val viewModel by viewModel<EventDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventConfigFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservable()
        retrieveArguments()
        setupClickListeners()
    }

    private fun setupObservable() {
        viewModel.getEventDetailState().observe(viewLifecycleOwner) {
            handleEventDetailState(it)
        }
    }

    private fun retrieveArguments() {
        viewModel.updateSelectedDate(
            arguments?.getInt(DATE_YEAR_KEY),
            arguments?.getInt(DATE_MONTH_KEY),
            arguments?.getInt(DATE_DAY_KEY)
        )
    }

    private fun setupClickListeners() {
        binding.saveButton.setOnClickListener {
            viewModel.scheduleEvent(
                binding.eventNameEditText.text.toString(),
                binding.eventDescriptionEditText.text.toString(),
                binding.eventTime.hour,
                binding.eventTime.minute,
            )
        }
    }

    private fun handleEventDetailState(eventDetailState: EventDetailState) {
        when (eventDetailState) {
            is EventDetailState.DateSelected -> setupEventDetailTitle(eventDetailState.eventDate)
            is EventDetailState.ErrorOnSave -> showGenericError()
            is EventDetailState.InvalidDateSelected -> handleInvalidDateSelected()
            is EventDetailState.EventSaved -> handleEventCreated(eventDetailState.agendaEvent)
            EventDetailState.EventScheduled -> handleEventScheduled()
            is EventDetailState.ErrorOnScheduleEvent -> showGenericError()
        }
    }

    private fun showGenericError(){
        requireContext()
            .defaultAlert(
                title = "Erro ao agendar evento :(",
                description = "Tente novamente",
                positiveButtonText = getString(android.R.string.ok),
                positiveButtonAction = {}
            )
    }

    private fun handleEventScheduled() {
        viewModel.saveEvent(
            binding.eventNameEditText.text.toString(),
            binding.eventDescriptionEditText.text.toString(),
            binding.eventTime.hour,
            binding.eventTime.minute,
        )
    }

    private fun handleInvalidDateSelected() = requireContext()
        .defaultAlert(
            title = "Seleção de data invalida :(",
            description = "Tente novamente",
            positiveButtonText = getString(android.R.string.ok),
            positiveButtonAction = {}
        )

    private fun handleErrorToSchedule() = requireContext()
        .defaultAlert(
            title = "Erro ao agendar evento :(",
            description = "Tente novamente",
            positiveButtonText = getString(android.R.string.ok),
            positiveButtonAction = {}
        )

    private fun handleEventCreated(agendaEvent: AgendaEvent) = requireContext()
        .defaultAlert(
            title = "Deu certo!",
            description = "Evento agendado para o dia ${agendaEvent.eventDate.day}/${agendaEvent.eventDate.month}/${agendaEvent.eventDate.year} as ${agendaEvent.eventTime.hour}:${agendaEvent.eventTime.minute}",
            positiveButtonText = getString(android.R.string.ok),
            positiveButtonAction = {
                navController.navigateUp()
            }
        )

    private fun setupEventDetailTitle(eventDate: EventDate) {
        binding.eventTitle.text =
            getString(
                R.string.event_detail_title_text,
                eventDate.day,
                eventDate.month,
                eventDate.year
            )
    }
}