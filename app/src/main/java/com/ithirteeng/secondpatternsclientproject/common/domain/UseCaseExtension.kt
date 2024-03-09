package com.ithirteeng.secondpatternsclientproject.common.domain

suspend fun <T> provideResult(execute: suspend () -> T): Result<T> {
    return try {
        Result.success(execute())
    } catch (e: Exception) {
        Result.failure(e)
    }
}
