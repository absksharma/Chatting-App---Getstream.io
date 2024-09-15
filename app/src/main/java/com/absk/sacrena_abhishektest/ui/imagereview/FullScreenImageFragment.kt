package com.absk.sacrena_abhishektest.ui.imagereview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.absk.sacrena_abhishektest.databinding.FragmentFullscreeenimageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScreenImageFragment : Fragment() {
    private lateinit var binding: FragmentFullscreeenimageBinding

    private val args: FullScreenImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullscreeenimageBinding.inflate(layoutInflater, container, false)

        setImages()
        setbackPress()

        return binding.root
    }

    private fun setImages() {
        binding.apply {
            args.imgURL.let { zoomableImageVIew.load(it) }
            args.imageDesciption.let { imageDescription.text = it }
            args.sendAt.let { tvSentAt.text = it }
        }
    }

    private fun setbackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}