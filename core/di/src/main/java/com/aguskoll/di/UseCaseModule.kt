package com.aguskoll.di

import com.aguskoll.usecases.GetCharacterByIdUseCase
import com.aguskoll.usecases.GetCharactersUseCase
import com.aguskoll.usecases.LoginUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single <LoginUseCase>{ LoginUseCase(get()) }
    single <GetCharactersUseCase>{ GetCharactersUseCase(get()) }
    single <GetCharacterByIdUseCase>{ GetCharacterByIdUseCase(get()) }
}
