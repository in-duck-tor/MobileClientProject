package com.ithirteeng.secondpatternsclientproject.features.common.login.auth

sealed class AuthEnvironment(
    val clientId: String,
    val redirectUri: String,
    val scope: String,
    val authorizationEndpointUri: String,
    val tokenEndpointUri: String,
    val responseType: String,
) {

    class OpenAuth : AuthEnvironment(
        clientId = "inductor_mobile_client",
        redirectUri = "com.ithirteeng.secondpatternsclientproject.app://yo",
        scope = "openid profile email",
        authorizationEndpointUri = "http://89.19.214.8:8180/connect/authorize",
        tokenEndpointUri = "http://89.19.214.8:8180/connect/token",
        responseType = "code"
    )
}


