package com.absk.sacrena_abhishektest.ui.chat.customviewholders

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import com.absk.sacrena_abhishektest.databinding.CustomSepratorItemBinding
import io.getstream.chat.android.ui.feature.messages.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListItem
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListItemPayloadDiff

class CustomSeparatorViewHolder(
    parentView: ViewGroup,
    private val binding: CustomSepratorItemBinding = CustomSepratorItemBinding.inflate(
        LayoutInflater.from(parentView.context), parentView, false
    )
) : BaseMessageItemViewHolder<MessageListItem.DateSeparatorItem>(binding.root) {
    override fun bindData(
        data: MessageListItem.DateSeparatorItem,
        diff: MessageListItemPayloadDiff
    ) {
        binding.tvSeparator.text = DateUtils.getRelativeTimeSpanString(
            data.date.time,
            System.currentTimeMillis(),
            DateUtils.DAY_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE,
        )
    }
}