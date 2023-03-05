package com.gustavo.agenda.eventDate.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gustavo.agenda.R
import com.gustavo.agenda.databinding.EventDateFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class EventDateFragment: Fragment(R.layout.event_date_fragment) {

    private lateinit var binding: EventDateFragmentBinding
    private val viewModel by viewModel<EventDateViewModel>()

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
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.addEventButton.setOnClickListener {
            AlertDialog.Builder(context)
                .setCancelable(true)
                .setMessage("Adicionar notificação para o dia ${viewModel.selectedDate}?")
                .setPositiveButton("Avançar") { dialog, id ->
                    val bundle = Bundle().apply {
                        putInt(DATE_DAY_KEY, binding.datePickerView.dayOfMonth)
                        putInt(DATE_MONTH_KEY, binding.datePickerView.month)
                        putInt(DATE_YEAR_KEY, binding.datePickerView.year)
                    }
                    findNavController().navigate(R.id.action_agendaFragment_to_eventFragment, bundle)
                }
                .setNegativeButton("Cancelar") {dialog, id ->

                }
                .create()
                .show()
        }
    }

    companion object {
        const val DATE_DAY_KEY = "date_day"
        const val DATE_MONTH_KEY = "date_month"
        const val DATE_YEAR_KEY = "date_year"
    }
}