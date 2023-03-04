package com.gustavo.agenda.presentation.di

import com.gustavo.agenda.presentation.agenda.AgendaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val agendaModule = module {
    viewModel { AgendaViewModel() }
}
