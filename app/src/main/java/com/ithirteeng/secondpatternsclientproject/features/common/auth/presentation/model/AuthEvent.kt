package com.ithirteeng.secondpatternsclientproject.features.common.auth.presentation.model

import android.content.Intent
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent

sealed interface AuthEvent : BaseEvent {
    data class IntentResultCame(val intent: Intent) : AuthEvent
}