package com.gustavo.agenda.di

import com.gustavo.agenda.ui.eventDetail.presentation.EventDetailViewModel
import com.gustavo.agenda.ui.eventdate.presentation.EventDateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { EventDetailViewModel(get(), get()) }
    viewModel { EventDateViewModel(get(), get()) }
}