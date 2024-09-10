package com.absk.sacrena_abhishektest.ui.chat.customviewholders

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import coil.load
import com.absk.sacrena_abhishektest.databinding.CustomTheirItemBinding
import com.absk.sacrena_abhishektest.utils.Utils.Companion.formatToTimeString
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.ui.feature.messages.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListItem
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListItem.MessageItem
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListItemPayloadDiff

class CustomTheirViewHolder(
    parentView: ViewGroup,
    private val binding: CustomTheirItemBinding = CustomTheirItemBinding.inflate(
        LayoutInflater.from(parentView.context), parentView, false
    )
) : BaseMessageItemViewHolder<MessageItem>(binding.root) {

    override fun bindData(data: MessageItem, diff: MessageListItemPayloadDiff) {
        binding.apply {
            tvMessage.text = data.message.text
            tvDate.text = formatToTimeString(data.message.createdAt)
            avatarImageView.load(data.message.user.image)
            onlineIndicator.visibility = if(data.message.user.online) VISIBLE else GONE
        }
    }
}


