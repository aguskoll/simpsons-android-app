package com.aguskoll.di

import AuthRepository
import com.aguskoll.data.network.ApiProvider
import com.aguskoll.data.network.AuthInterceptor
import com.aguskoll.data.network.HttpClientProvider
import com.aguskoll.data.network.HttpClientProviderImpl
import com.aguskoll.data.repositories.AuthRepositoryImpl
import com.aguskoll.data.store.PreferencesDataStore
import com.aguskoll.data.utils.Constants.MegaBytes50
import com.aguskoll.data.utils.CoroutineErrorHandler
import com.aguskoll.domain.errors.ErrorHandler
import com.aguskoll.domain.errors.ErrorNotifier
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope
import org.koin.dsl.module

val dataModule = module {
    single { androidApplication().resources }
    single { ErrorNotifier() }
    single { createJson() }
    single { createPreferencesDataStore() }
    single { AuthInterceptor(get()) }
    single<HttpClientProvider> { createHttpClientProvider() }
    single { createApiProvider() }
    single { get<ApiProvider>().myApi }

    factory { Cache(androidApplication().cacheDir, MegaBytes50) }
    factory<ErrorHandler> {
        CoroutineErrorHandler(
            errorNotifier = get()
        )
    }
    single<AuthRepository> { AuthRepositoryImpl() }
}

@OptIn(ExperimentalSerializationApi::class)
private fun createJson() = Json {
    explicitNulls = false
    ignoreUnknownKeys = true
    isLenient = true
    useAlternativeNames = false
}

private fun Scope.createApiProvider() = ApiProvider(
    json = get(),
    httpClientProvider = get()
)

private fun Scope.createPreferencesDataStore() = PreferencesDataStore(
    coroutineDispatcher = Dispatchers.IO,
    context = androidContext(),
    preferencesFileName = "app_prefs"
)

private fun Scope.createHttpClientProvider() =
    HttpClientProviderImpl(
        cache = get(),
        authInterceptor = get()
    )
