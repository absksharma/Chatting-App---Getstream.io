package com.absk.sacrena_abhishektest.listeners

import io.getstream.chat.android.models.Message

interface MessageItemListener {
    fun onAttachmentClick(message: Message)
}