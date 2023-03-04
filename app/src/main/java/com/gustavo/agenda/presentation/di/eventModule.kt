package com.gustavo.agenda.presentation.di

import com.gustavo.agenda.presentation.event.presentation.EventViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventModule = module {
    viewModel { EventViewModel() }
}
