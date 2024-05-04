package com.ithirteeng.secondpatternsclientproject.common.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.UUID

class IdempotencyInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        if (request.header(HEADER) == null) {
            builder.addHeader(HEADER, UUID.randomUUID().toString())
        }
        return chain.proceed(builder.build())
    }

    private companion object {
        private const val HEADER = "Idempotency-Key"
    }
}