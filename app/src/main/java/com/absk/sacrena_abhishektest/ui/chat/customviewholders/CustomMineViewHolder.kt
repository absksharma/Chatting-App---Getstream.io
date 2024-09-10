package com.absk.sacrena_abhishektest.ui.chat.customviewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.absk.sacrena_abhishektest.databinding.CustomMineItemBinding
import com.absk.sacrena_abhishektest.utils.Utils.Companion.formatToTimeString
import io.getstream.chat.android.ui.feature.messages.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListItem
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListItemPayloadDiff

class CustomMineViewHolder(
    parentView: ViewGroup,
    private val binding: CustomMineItemBinding = CustomMineItemBinding.inflate(
        LayoutInflater.from(parentView.context), parentView, false
    )
) : BaseMessageItemViewHolder<MessageListItem.MessageItem>(binding.root) {
    override fun bindData(data: MessageListItem.MessageItem, diff: MessageListItemPayloadDiff) {
        binding.tvMessage.text = data.message.text
        binding.tvDate.text = formatToTimeString(data.message.createdAt)

        binding.root.setOnClickListener {

        }
    }
}
