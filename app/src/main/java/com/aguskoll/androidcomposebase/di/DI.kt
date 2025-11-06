package com.aguskoll.androidcomposebase.di

import com.aguskoll.androidcomposebase.ui.AppActivityViewModel
import com.aguskoll.androidcomposebase.ui.pages.login.LogInViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
/**
 *  app module provides viewModels and other objects in the app module
 *  For example: viewModel{}, single{}, factory{}
 * */
val appModule = module {
    viewModel { AppActivityViewModel(get()) }
    viewModel<LogInViewModel> { LogInViewModel(get()) }
}
