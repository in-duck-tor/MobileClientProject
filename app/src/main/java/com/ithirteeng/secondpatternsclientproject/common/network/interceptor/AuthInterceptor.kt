package com.ithirteeng.secondpatternsclientproject.common.network.interceptor

import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetLocalTokenUseCase
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val getLocalTokenUseCase: GetLocalTokenUseCase,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        if (request.header(AUTHORIZATION_HEADER) == null) {
            getLocalTokenUseCase().let {
                builder.addHeader(
                    AUTHORIZATION_HEADER,
                    "$BEARER ${it?.accessToken}"
                )
            }
        }

        return chain.proceed(builder.build())
    }

    private companion object {

        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER = "Bearer"
    }
}