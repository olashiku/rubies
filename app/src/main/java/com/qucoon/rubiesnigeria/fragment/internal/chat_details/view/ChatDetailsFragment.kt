package com.qucoon.rubiesnigeria.fragment.internal.chat_details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.base.BaseFragment
import com.qucoon.rubiesnigeria.databinding.FragmentChatBinding
import com.qucoon.rubiesnigeria.databinding.FragmentChatDetailsBinding


class ChatDetailsFragment : BaseFragment() {

    lateinit var binding: FragmentChatDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavigation(true)
        setupClickListener()
    }

    private fun setupClickListener() {
        binding.statConversationButton.setOnClickListener {
            openFragment(R.id.action_chatFragment_to_contactFragment)
        }
    }

}