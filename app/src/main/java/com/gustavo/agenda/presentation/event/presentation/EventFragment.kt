package com.gustavo.agenda.presentation.event.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.gustavo.agenda.R
import com.gustavo.agenda.databinding.AgendaEventFragmentBinding
import com.gustavo.agenda.presentation.date.AgendaFragment.Companion.SELECTED_DATE_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventFragment: Fragment(R.layout.agenda_event_fragment) {

    private lateinit var binding: AgendaEventFragmentBinding
    private val navController by lazy { Navigation.findNavController(binding.root) }
    private val viewModel by viewModel<EventViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AgendaEventFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.eventTitle.text = "Configurar evento para o dia " + arguments?.getString(SELECTED_DATE_KEY) ?: ""
        binding.saveButton.setOnClickListener {
//            viewModel.saveEvent()
//            val localDate: LocalDate = LocalDate.parse()
            navController.navigateUp()
        }
    }
}