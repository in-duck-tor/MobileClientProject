package com.ithirteeng.secondpatternsclientproject.data.theme.datasource

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.ithirteeng.secondpatternsclientproject.common.mapper.toFirebaseLogin
import com.ithirteeng.secondpatternsclientproject.domain.theme.datasource.ThemeRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import kotlinx.coroutines.tasks.await

class ThemeRemoteDatasourceImpl(
    private val context: Context,
    private val firebaseDatabase: DatabaseReference,
) : ThemeRemoteDatasource {

    override suspend fun getTheme(login: String): Theme? {
        return if (isNetworkAvailable()) {
            try {
                val theme = firebaseDatabase.child(APP_THEME).child(USERS).child(login.toFirebaseLogin()).get()
                theme.await().value?.let { Theme.valueOf(it.toString()) }
            } catch (e: Exception) {
                Log.e("FIREBASE", e.message.toString())
                null
            }
        } else {
            null
        }
    }

    override fun updateTheme(login: String, newTheme: Theme) {
        firebaseDatabase.child(APP_THEME).child(USERS).child(login.toFirebaseLogin())
            .setValue(newTheme.name)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    private companion object {

        const val APP_THEME = "app_theme"
        const val USERS = "users"
    }
}