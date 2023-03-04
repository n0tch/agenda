package com.gustavo.agenda.eventDate

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gustavo.agenda.R
import com.gustavo.agenda.databinding.AgendaFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class EventDateFragment: Fragment(R.layout.agenda_fragment) {

    private lateinit var binding: AgendaFragmentBinding
    private val viewModel by viewModel<EventDateViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AgendaFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarView.date = Calendar.getInstance().timeInMillis

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.calendarView.setOnDateChangeListener { calendarView, year, month, day ->
            Log.e("Selected", "$day/$month/$year")
            viewModel.selectedDate = "$day/${month + 1}/$year"
            binding.eventListTextView.text = viewModel.selectedDate
        }

        binding.addEventButton.setOnClickListener {
            AlertDialog.Builder(context)
                .setCancelable(true)
                .setMessage("Adicionar notificação para o dia ${viewModel.selectedDate}?")
                .setPositiveButton("Avançar") { dialog, id ->
                    val bundle = Bundle().apply {
                        putString(SELECTED_DATE_KEY, viewModel.selectedDate)
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
        const val SELECTED_DATE_KEY = "selected_date"
    }
}