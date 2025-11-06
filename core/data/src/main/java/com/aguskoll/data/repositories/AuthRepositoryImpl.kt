package com.aguskoll.data.repositories

import AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository{

    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
       // Simulate delay
        delay(2000)
        return Result.success(Unit)
    }
}
