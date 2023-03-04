package com.gustavo.agenda.di

import com.gustavo.agenda.date.AgendaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val agendaModule = module {
    viewModel { AgendaViewModel() }
}
