package com.absk.sacrena_abhishektest.app

import android.app.Application
import androidx.startup.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.ui.initializer.ChatUIInitializer
import javax.inject.Inject

@HiltAndroidApp
class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppInitializer.getInstance(applicationContext).initializeComponent(ChatUIInitializer::class.java)
    }
}