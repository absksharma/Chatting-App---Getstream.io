package com.absk.sacrena_abhishektest.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.getstream.chat.android.client.ChatClient
import javax.inject.Inject

@HiltAndroidApp
class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}