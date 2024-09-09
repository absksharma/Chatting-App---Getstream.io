package com.absk.sacrena_abhishektest.di

import android.content.Context
import com.absk.sacrena_abhishektest.R
import com.absk.sacrena_abhishektest.customviewholders.CustomChannelListItemViewHolderFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.channel.ChannelClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private fun getState(@ApplicationContext context: Context) = StreamStatePluginFactory(
        config = StatePluginConfig(
            backgroundSyncEnabled = true,
            userPresence = true
        ),
        appContext = context
    )

    @Provides
    @Singleton
    fun getChatClient(@ApplicationContext context: Context) = ChatClient.Builder(
        context.getString(
            R.string.api_key
        ), context
    ).logLevel(ChatLogLevel.ALL).withPlugins(getState(context)).build()

    @Provides
    @Singleton
    fun getChannel(@ApplicationContext context: Context): ChannelClient =
        getChatClient(context).channel("messaging", "alice_bob_chat")

    @Provides
    @Singleton
    fun getCustomChannelViewHolder(): CustomChannelListItemViewHolderFactory =
        CustomChannelListItemViewHolderFactory()
}