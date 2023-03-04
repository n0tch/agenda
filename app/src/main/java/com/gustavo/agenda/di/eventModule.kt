package com.gustavo.agenda.di

import com.gustavo.agenda.event.presentation.EventViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventModule = module {
    viewModel { EventViewModel() }
}
