package com.absk.sacrena_abhishektest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.absk.sacrena_abhishektest.customviewholders.CustomMessageViewHolderFactory
import com.absk.sacrena_abhishektest.databinding.FragmentChatDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.ui.common.state.messages.Edit
import io.getstream.chat.android.ui.common.state.messages.MessageMode
import io.getstream.chat.android.ui.common.state.messages.Reply
import io.getstream.chat.android.ui.feature.channels.ChannelListFragment
import io.getstream.chat.android.ui.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListHeaderViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModelFactory
import io.getstream.chat.android.ui.viewmodel.messages.bindView

@AndroidEntryPoint
class ChatDetailsFragment : Fragment() {
    private lateinit var binding: FragmentChatDetailsBinding
    private val args: ChatDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatDetailsBinding.inflate(layoutInflater, container, false)

        setupMessages()

        binding.messageListHeaderView.setBackButtonClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun setupMessages() {
        binding.apply {
            val factory = MessageListViewModelFactory(requireActivity(), args.channelId)

            val messageListHeaderViewModel: MessageListHeaderViewModel by viewModels { factory }
            val messageListViewModel: MessageListViewModel by viewModels { factory }
            val messageInputViewModel: MessageComposerViewModel by viewModels { factory }


            messageListView.apply {
                setMessageViewHolderFactory(CustomMessageViewHolderFactory())
                setShowAvatarPredicate { messageItem ->
                    messageItem.isTheirs
                }

            }
            messageListHeaderViewModel.bindView(messageListHeaderView, viewLifecycleOwner)
            messageListViewModel.bindView(messageListView, viewLifecycleOwner)
            messageInputViewModel.bindView(messageComposerView, viewLifecycleOwner)

            messageListViewModel.mode.observe(viewLifecycleOwner) { mode ->
                when (mode) {
                    is MessageMode.MessageThread -> {
                        messageInputViewModel.setMessageMode(MessageMode.MessageThread(mode.parentMessage))
                    }
                    is MessageMode.Normal -> {
                        messageInputViewModel.leaveThread()
                    }
                }
            }
            messageListView.setMessageReplyHandler { _, message ->
                messageInputViewModel.performMessageAction(Reply(message))
            }
            messageListView.setMessageEditHandler { message ->
                messageInputViewModel.performMessageAction(Edit(message))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


}