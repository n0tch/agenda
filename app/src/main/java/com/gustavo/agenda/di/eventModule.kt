package com.gustavo.agenda.di

import com.gustavo.agenda.eventDetail.presentation.EventDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventModule = module {
    viewModel { EventDetailViewModel() }
}
