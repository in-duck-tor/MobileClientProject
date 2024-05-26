package com.ithirteeng.secondpatternsclientproject.features.common.auth.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import com.r3d1r4ph.gevorkyanoffbank.managers.auth.model.AppAuthHttpConnectionBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import net.openid.appauth.AppAuthConfiguration
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthManager(context: Context) {

	private val auth = AuthEnvironment.OpenAuth()
	private val serviceConfig: AuthorizationServiceConfiguration = AuthorizationServiceConfiguration(
		Uri.parse(auth.authorizationEndpointUri),
		Uri.parse(auth.tokenEndpointUri),
		null
	)
	private val authService: AuthorizationService by lazy {
		AuthorizationService(
			context,
			AppAuthConfiguration.Builder()
				.setConnectionBuilder(AppAuthHttpConnectionBuilder.INSTANCE)
				.build()
		)
	}

	fun getRequestIntent(): Intent {
		return authService.getAuthorizationRequestIntent(buildRequest())
	}

	private fun buildRequest(): AuthorizationRequest =
		AuthorizationRequest.Builder(
			serviceConfig,
			auth.clientId,
			auth.responseType,
			auth.redirectUri.toUri()
		)
			.setScope(auth.scope)
			.build()

	suspend fun getTokenFromAuthorizationResponseIntent(intent: Intent): String =
		withContext(Dispatchers.IO) {
			suspendCoroutine { continuation ->
				AuthorizationResponse.fromIntent(intent)?.let { authorizationResponse ->
					authService.performTokenRequest(
						authorizationResponse.createTokenExchangeRequest()
					) { tokenResponse, exception ->
						tokenResponse?.accessToken?.let {
							continuation.resume(it)
						} ?: continuation.resumeWithException(exception ?: Exception("getTokenFromAuthorizationResponseIntent"))
					}
				} ?: continuation.resumeWithException(Exception("getTokenFromAuthorizationResponseIntent"))
			}
		}

	companion object {

		private var instance: AuthManager? = null

		fun getInstance(context: Context): AuthManager =
			instance ?: AuthManager(context).also { instance = it }
	}
}