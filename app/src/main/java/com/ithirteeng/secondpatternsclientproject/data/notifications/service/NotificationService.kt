package com.ithirteeng.secondpatternsclientproject.data.notifications.service

import com.ithirteeng.secondpatternsclientproject.data.notifications.model.AppRegistrationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface NotificationService {

    @PUT("vestnik/app-registration")
    suspend fun registerAppForNotifications(@Body appRegistrationModel: AppRegistrationModel): Response<Unit>
}