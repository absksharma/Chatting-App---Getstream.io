package com.absk.sacrena_abhishektest.ui.chat.customviewholders

import android.view.ViewGroup
import com.absk.sacrena_abhishektest.listeners.MessageItemListener
import io.getstream.chat.android.ui.feature.messages.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListItem
import io.getstream.chat.android.ui.feature.messages.list.adapter.MessageListItemViewHolderFactory

class CustomMessageViewHolderFactory(
    private val itemListener: MessageItemListener
) : MessageListItemViewHolderFactory() {

    override fun getItemViewType(item: MessageListItem): Int {
        return when {
            item is MessageListItem.MessageItem && item.isTheirs -> {
                THEIR_VIEW_HOLDER_TYPE
            }

            item is MessageListItem.MessageItem && item.isMine -> {
                MINE_VIEW_HOLDER_TYPE
            }

            item is MessageListItem.DateSeparatorItem -> {
                SEPARATOR_VIEW_HOLDER_TYPE
            }

            item is MessageListItem.StartOfTheChannelItem -> {
                STARTCHANNEL_VIEW_HOLDER_TYPE
            }

            else -> {
                -1
            }
        }
    }

    override fun getItemViewType(viewHolder: BaseMessageItemViewHolder<out MessageListItem>): Int {
        return when (viewHolder) {
            is CustomTheirViewHolder -> {
                THEIR_VIEW_HOLDER_TYPE
            }

            is CustomMineViewHolder -> {
                MINE_VIEW_HOLDER_TYPE
            }

            is CustomSeparatorViewHolder -> {
                SEPARATOR_VIEW_HOLDER_TYPE
            }

            else -> {
                STARTCHANNEL_VIEW_HOLDER_TYPE
            }
        }
    }


    override fun createViewHolder(
        parentView: ViewGroup,
        viewType: Int,
    ): BaseMessageItemViewHolder<out MessageListItem> {
        return when (viewType) {
            THEIR_VIEW_HOLDER_TYPE -> {
                CustomTheirViewHolder(itemListener, parentView)
            }

            MINE_VIEW_HOLDER_TYPE -> {
                CustomMineViewHolder(itemListener, parentView)
            }

            SEPARATOR_VIEW_HOLDER_TYPE -> {
                CustomSeparatorViewHolder(parentView)
            }

            else -> {
                EmptyViewHolder(parentView)
            }
        }
    }

    companion object {
        private const val THEIR_VIEW_HOLDER_TYPE = 1
        private const val MINE_VIEW_HOLDER_TYPE = 2
        private const val SEPARATOR_VIEW_HOLDER_TYPE = 3
        private const val STARTCHANNEL_VIEW_HOLDER_TYPE = 4
    }
}


