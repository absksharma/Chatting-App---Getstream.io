package com.absk.sacrena_abhishektest.ui.chat

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.absk.sacrena_abhishektest.R
import com.absk.sacrena_abhishektest.databinding.FragmentChatDetailsBinding
import com.absk.sacrena_abhishektest.ui.chat.customviewholders.CustomMessageViewHolderFactory
import com.absk.sacrena_abhishektest.utils.Utils.Companion.getInitials
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.models.Message
import io.getstream.chat.android.ui.common.state.messages.Edit
import io.getstream.chat.android.ui.common.state.messages.MessageMode
import io.getstream.chat.android.ui.common.state.messages.Reply
import io.getstream.chat.android.ui.feature.messages.list.MessageListView
import io.getstream.chat.android.ui.viewmodel.messages.MessageComposerViewModel
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
    ): View {
        binding = FragmentChatDetailsBinding.inflate(layoutInflater, container, false)

        setUpHeader()
        setupMessages()
        return binding.root
    }

    private fun setUpHeader() {
        binding.messageListHeaderView.apply {
            titleTextView.text = args.channelName
            if (args.cahnnelImage.isNotEmpty()) {
                avatarImageView.load(args.cahnnelImage)
                tvUserName.visibility = INVISIBLE
                avatarImageView.visibility = View.VISIBLE
            } else {
                tvUserName.text = getInitials(args.channelName)
                avatarImageView.visibility = INVISIBLE
                tvUserName.visibility = View.VISIBLE
            }
        }
    }

    private fun setupMessages() {
        binding.apply {

            val factory = MessageListViewModelFactory(requireActivity(), args.channelId)

            val messageListViewModel: MessageListViewModel by viewModels { factory }
            val messageInputViewModel: MessageComposerViewModel by viewModels { factory }


            messageListViewModel.bindView(messageListView, viewLifecycleOwner)
            messageInputViewModel.bindView(messageComposerView, viewLifecycleOwner)
            messageListView.apply {
                setMessageViewHolderFactory(CustomMessageViewHolderFactory())
                setReactionsEnabled(true)
                setDeleteMessageEnabled(true)
                setEditMessageEnabled(true)
                setOnUserReactionClickListener(null)
            }
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

            val textView = TextView(context).apply {
                text = "This is the beginning of your conversation\nwith ${args.channelName}"
                setTextColor(ContextCompat.getColor(context, R.color.text_hint))
            }
            messageListView.setEmptyStateView(
                view = textView,
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER
                )
            )
            messageListView.setReactionsEnabled(true)
            val backHandler = {
                messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
            }

            binding.messageListHeaderView.backButton.setOnClickListener {
                backHandler()
                findNavController().popBackStack()
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                backHandler()
                findNavController().popBackStack()
            }
        }
    }
}