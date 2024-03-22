package com.ithirteeng.secondpatternsclientproject.data.theme.datasource

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.database.DatabaseReference
import com.ithirteeng.secondpatternsclientproject.domain.theme.datasource.ThemeRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import kotlinx.coroutines.tasks.await

class ThemeRemoteDatasourceImpl(
    private val firebaseDatabase: DatabaseReference,
) : ThemeRemoteDatasource {

    override suspend fun getTheme(login: String): Theme? {
        return try {
            val theme = firebaseDatabase.child(APP_THEME).child(USERS).child(login).get().await()
            theme.value?.let { Theme.valueOf(it.toString()) }
        } catch (e: FirebaseException) {
            Log.e("FIREBASE", e.message.toString())
            null
        }
    }

    override fun updateTheme(login: String, newTheme: Theme) {
        firebaseDatabase.child(APP_THEME).child(USERS).child(login)
            .setValue(newTheme.name)
    }

    private companion object {

        const val APP_THEME = "app_theme"
        const val USERS = "users"
    }
}