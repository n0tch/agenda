package com.gustavo.agenda.di

import com.gustavo.agenda.eventDetail.business.EventDetailInteractor
import com.gustavo.agenda.eventDetail.domain.repository.EventDetailRepository
import com.gustavo.agenda.eventDetail.presentation.EventDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventModule = module {
    factory { EventDetailInteractor(get(), get()) }
    factory { EventDetailRepository(get(), get(), get()) }
    viewModel { EventDetailViewModel(get()) }
}
