package com.absk.sacrena_abhishektest.ui.chat

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.enableSavedStateHandles
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.absk.sacrena_abhishektest.R
import com.absk.sacrena_abhishektest.databinding.FragmentChatDetailsBinding
import com.absk.sacrena_abhishektest.listeners.MessageItemListener
import com.absk.sacrena_abhishektest.ui.chat.customviewholders.CustomMessageViewHolderFactory
import com.absk.sacrena_abhishektest.utils.Utils.Companion.getInitials
import com.absk.sacrena_abhishektest.utils.Utils.Companion.toTimeAgo
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.models.Message
import io.getstream.chat.android.ui.common.state.messages.Edit
import io.getstream.chat.android.ui.common.state.messages.MessageMode
import io.getstream.chat.android.ui.common.state.messages.Reply
import io.getstream.chat.android.ui.common.state.messages.list.DeleteMessage
import io.getstream.chat.android.ui.common.state.messages.list.EditMessage
import io.getstream.chat.android.ui.common.state.messages.list.SendAnyway
import io.getstream.chat.android.ui.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.viewmodel.messages.MessageListViewModelFactory
import io.getstream.chat.android.ui.viewmodel.messages.bindView

@AndroidEntryPoint
class ChatDetailsFragment : Fragment(), MessageItemListener {
    private var _binding: FragmentChatDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: ChatDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentChatDetailsBinding.inflate(layoutInflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpHeader()
        setEmptyView()
        setupMessages()
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

    private fun setEmptyView() {
        val textView = TextView(context).apply {
            text = "This is the beginning of your conversation\nwith ${args.channelName}"
            setTextColor(ContextCompat.getColor(context, R.color.text_hint))
        }
        binding.messageListView.setEmptyStateView(
            view = textView, layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
        )
    }

    private fun setupMessages() {
        binding.apply {
            val factory = MessageListViewModelFactory(requireActivity(), args.channelId)
            val messageListViewModel: MessageListViewModel by viewModels { factory }
            val messageInputViewModel: MessageComposerViewModel by viewModels { factory }

            messageListViewModel.bindView(messageListView, viewLifecycleOwner)
            messageInputViewModel.bindView(messageComposerView, viewLifecycleOwner)

            if (!messageListView.isAdapterInitialized()) {
                messageListView.apply {
                    setMessageViewHolderFactory(CustomMessageViewHolderFactory(this@ChatDetailsFragment))
//                setReactionsEnabled(true)
                }
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

            val backHandler = {
                messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
            }

            messageListHeaderView.backButton.setOnClickListener {
                backHandler()
                findNavController().popBackStack()
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                backHandler()
                findNavController().popBackStack()
            }
        }
    }

    override fun onAttachmentClick(message: Message) {
        val action = ChatDetailsFragmentDirections.actionChatDetailsFragmentToImagepreviewFragment(
            imgURL = message.attachments[0].imageUrl.toString(),
            imageDesciption = message.attachments[0].name,
            sendAt = message.createdAt?.toTimeAgo
        )
        binding.root.findNavController().navigate(action)
    }
}