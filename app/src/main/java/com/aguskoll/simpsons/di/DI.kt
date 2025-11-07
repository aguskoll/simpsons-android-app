package com.aguskoll.simpsons.di

import com.aguskoll.simpsons.ui.AppActivityViewModel
import com.aguskoll.simpsons.ui.pages.characters.CharactersViewModel
import com.aguskoll.simpsons.ui.pages.characters.detail.CharacterDetailViewModel
import com.aguskoll.simpsons.ui.pages.login.LogInViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
/**
 *  app module provides viewModels and other objects in the app module
 *  For example: viewModel{}, single{}, factory{}
 * */
val appModule = module {
    viewModel { AppActivityViewModel(get()) }
    viewModel<LogInViewModel> { LogInViewModel(get()) }
    viewModel<CharactersViewModel> { CharactersViewModel(get()) }
    viewModel<CharacterDetailViewModel> { CharacterDetailViewModel(get()) }
}
