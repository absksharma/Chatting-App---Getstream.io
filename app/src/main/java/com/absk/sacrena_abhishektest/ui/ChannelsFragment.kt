package com.absk.sacrena_abhishektest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.absk.sacrena_abhishektest.customviewholders.CustomChannelListItemViewHolderFactory
import com.absk.sacrena_abhishektest.databinding.FragmentChannelsBinding
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.User
import io.getstream.chat.android.models.querysort.QuerySortByField
import io.getstream.chat.android.ui.feature.channels.list.ChannelListView
import io.getstream.chat.android.ui.viewmodel.channels.ChannelListViewModel
import io.getstream.chat.android.ui.viewmodel.channels.ChannelListViewModelFactory
import io.getstream.chat.android.ui.viewmodel.channels.bindView
import javax.inject.Inject

@AndroidEntryPoint
class ChannelsFragment : Fragment(),
    ChannelListView.ChannelClickListener {
    private lateinit var binding: FragmentChannelsBinding

    @Inject
    lateinit var client: ChatClient

    @Inject
    lateinit var channelClient: ChatClient

    @Inject
    lateinit var customChannelViewholder: CustomChannelListItemViewHolderFactory

    companion object {
        val user = User(
            id = "Alice_601bf73a-39b7-4630-929d-7560506e7406",
            name = "Alice",
            image = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChannelsBinding.inflate(inflater, container, false)
        setUpChannel()

        return binding.root
    }


    private fun setUpChannel() {
        client.connectUser(user = user, token = client.devToken(user.id)).enqueue {
            if (it.isSuccess) {
                val channelListFactory = ChannelListViewModelFactory(
                    filter = Filters.and(
                        Filters.eq("type", "messaging"),
                        Filters.`in`("members", listOf(user.id)),
                    ),
                    sort = QuerySortByField.descByName("last_updated"),
                    limit = 30,
                )
                binding.channelListView.setViewHolderFactory(customChannelViewholder)

                val listViewModel: ChannelListViewModel by viewModels { channelListFactory }
                listViewModel.bindView(binding.channelListView, viewLifecycleOwner)
                binding.channelListView.setChannelItemClickListener(this)
            } else {
                Toast.makeText(requireActivity(), "something went wrong!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onClick(channel: Channel) {
        val action =
            ChannelsFragmentDirections.actionChannelsFragmentToChatDetailsFragment(channel.cid)
        binding.root.findNavController().navigate(action)
    }
}