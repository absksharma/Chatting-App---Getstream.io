package com.absk.sacrena_abhishektest.ui.chat.customviewholders

import android.view.View
import android.view.ViewGroup
import io.getstream.chat.android.ui.feature.messages.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListItem
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListItemPayloadDiff

class EmptyViewHolder(
    parentView: ViewGroup,
) : BaseMessageItemViewHolder<MessageListItem>(View(parentView.context)) {
    override fun bindData(data: MessageListItem, diff: MessageListItemPayloadDiff) = Unit
}
