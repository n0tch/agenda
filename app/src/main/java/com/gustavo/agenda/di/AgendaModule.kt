package com.gustavo.agenda.di

import com.gustavo.agenda.eventDate.business.EventDateInteractor
import com.gustavo.agenda.eventDate.domain.repository.EventDateRepository
import com.gustavo.agenda.eventDate.presentation.EventDateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val agendaModule = module {
    factory { EventDateInteractor(get()) }
    factory { EventDateRepository(get()) }
    viewModel { EventDateViewModel(get()) }
}
