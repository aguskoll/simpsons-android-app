package com.aguskoll.di

import com.aguskoll.usecases.LoginUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single <LoginUseCase>{ LoginUseCase(get()) }
}
