package com.absk.sacrena_abhishektest.customviewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.absk.sacrena_abhishektest.databinding.TodayMessageListItemBinding
import io.getstream.chat.android.ui.feature.messages.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListItem
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListItemPayloadDiff

class TodayViewHolder(
    parentView: ViewGroup,
    private val binding: TodayMessageListItemBinding = TodayMessageListItemBinding.inflate(
        LayoutInflater.from(parentView.context), parentView, false
    )
) : BaseMessageItemViewHolder<MessageListItem.MessageItem>(binding.root) {
    override fun bindData(data: MessageListItem.MessageItem, diff: MessageListItemPayloadDiff) {
        binding.textLabel.text = data.message.text
    }
}


