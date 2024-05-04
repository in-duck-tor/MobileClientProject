package com.ithirteeng.secondpatternsclientproject.data.accounts.datasource

import android.util.Log
import com.google.gson.Gson
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.TransactionCreatedEvent
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.TransactionUpdatedEvent
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetLocalTokenUseCase
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import com.microsoft.signalr.TransportEnum
import io.reactivex.rxjava3.core.Single

class SocketsDatasource(
    private val getLocalTokenUseCase: GetLocalTokenUseCase,
    private val onTransactionCreated: (TransactionCreatedEvent) -> Unit = {},
    private val onTransactionUpdated: (TransactionUpdatedEvent) -> Unit = {},
) {

    fun connectToWebSocket() {
        val gson = Gson()
        val hubConnection = HubConnectionBuilder
            .create("http://89.19.214.8/api/v1/ws/vestnik/account-events")
            .withAccessTokenProvider(Single.defer {
                Single.just(
                    getLocalTokenUseCase()?.accessToken.toString()
                )
            })
            .withTransport(TransportEnum.ALL)
            .build()
        hubConnection.on(
            "AccountCreated",
            { message ->
                Log.d(
                    "$TAG-AccountCreated",
                    "New Message: ${gson.toJson(message)}"
                )
            },
            Object::class.java
        )
        hubConnection.on(
            "AccountUpdated",
            { message ->
                Log.d(
                    "$TAG-AccountUpdated",
                    "New Message: ${gson.toJson(message)}"
                )
            },
            Object::class.java
        )
        hubConnection.on(
            "TransactionCreated",
            { message ->
                val json = gson.toJson(message)
                Log.d(
                    "$TAG-TransactionCreated",
                    "New Message: ${gson.fromJson(json, TransactionCreatedEvent::class.java)}"
                )
                onTransactionCreated(gson.fromJson(json, TransactionCreatedEvent::class.java))
            },
            Object::class.java
        )
        hubConnection.on(
            "TransactionUpdated",
            { message ->
                val json = gson.toJson(message)
                Log.d(
                    "$TAG-TransactionUpdated",
                    "New Message: ${gson.fromJson(json, TransactionUpdatedEvent::class.java)}"
                )
                onTransactionUpdated(gson.fromJson(json, TransactionUpdatedEvent::class.java))
            },
            Object::class.java
        )
        hubConnection.on(
            "Hueta",
            { message -> Log.d("$TAG-Hueta", "New Message: ${gson.toJson(message)}") },
            Object::class.java
        )
        hubConnection.start()
            .blockingSubscribe {
                when (hubConnection.connectionState) {
                    HubConnectionState.CONNECTED -> Log.d("$TAG-state", "connected")
                    HubConnectionState.DISCONNECTED -> Log.d("$TAG-state", "closed")
                    HubConnectionState.CONNECTING -> Log.d("$TAG-state", "connecting")
                    else -> Log.d("$TAG-state", "null")
                }
                hubConnection.invoke("SubscribeToMyAccounts").blockingSubscribe {
                    Log.d("$TAG-method", "SubscribeToMyAccounts invoked")
                }
            }
    }

    private companion object {
        private const val TAG = "SOCKETS"
    }
}