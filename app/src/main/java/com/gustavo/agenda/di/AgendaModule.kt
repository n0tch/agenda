package com.gustavo.agenda.di

import com.gustavo.agenda.eventDate.presentation.EventDateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val agendaModule = module {
    viewModel { EventDateViewModel() }
}
