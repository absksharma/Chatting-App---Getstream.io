package com.absk.sacrena_abhishektest.customviewholders

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.absk.sacrena_abhishektest.databinding.CustomChannelListItemBinding
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.ui.ChatUI
import io.getstream.chat.android.ui.feature.channels.list.ChannelListView
import io.getstream.chat.android.ui.feature.channels.list.adapter.ChannelListItem
import io.getstream.chat.android.ui.feature.channels.list.adapter.ChannelListPayloadDiff
import io.getstream.chat.android.ui.feature.channels.list.adapter.viewholder.BaseChannelListItemViewHolder
import io.getstream.chat.android.ui.feature.channels.list.adapter.viewholder.ChannelListItemViewHolderFactory
import io.getstream.chat.android.ui.utils.extensions.getLastMessage

class CustomChannelListItemViewHolderFactory : ChannelListItemViewHolderFactory() {
    override fun createChannelViewHolder(parentView: ViewGroup): BaseChannelListItemViewHolder {
        return CustomChannelViewHolder(parentView, listenerContainer.channelClickListener)
    }
}

class CustomChannelViewHolder(
    parent: ViewGroup,
    private val channelClickListener: ChannelListView.ChannelClickListener,
    private val binding: CustomChannelListItemBinding = CustomChannelListItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
) : BaseChannelListItemViewHolder(binding.root) {

    private lateinit var channel: Channel

    init {
        binding.root.setOnClickListener { channelClickListener.onClick(channel) }
    }

    override fun bind(channelItem: ChannelListItem.ChannelItem, diff: ChannelListPayloadDiff) {
        this.channel = channelItem.channel

        binding.apply {
//            avatarImageView.setImageURI(channelItem.channel.image)
            tvChannelName.text = ChatUI.channelNameFormatter.formatChannelName(
                channel = channelItem.channel,
                currentUser = ChatClient.instance().getCurrentUser()
            )
            lastMessage.text = channelItem.channel.getLastMessage()?.text

            onlineIndicator.visibility = if (channelItem.channel.members.find {
                    it.user.online == true
                            && it.user.id != ChatClient.instance().getCurrentUser()?.id
                } != null) {
                VISIBLE
            } else {
                GONE
            }

        }
    }
}