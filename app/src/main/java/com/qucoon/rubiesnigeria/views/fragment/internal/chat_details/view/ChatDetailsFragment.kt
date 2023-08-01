package com.qucoon.rubiesnigeria.views.fragment.internal.chat_details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.base.BaseFragment
import com.qucoon.rubiesnigeria.databinding.FragmentChatDetailsBinding
import com.qucoon.rubiesnigeria.viewmodel.AuthViewModel
import com.qucoon.rubiesnigeria.viewmodel.ScarletSocketViewModel
import com.qucoon.rubiesnigeria.viewmodel.SocketAuthenticationViewModel
import com.qucoon.rubiesnigeria.views.fragment.internal.friends.adapter.FriendonRubiesFragment
import org.koin.android.viewmodel.ext.android.viewModel


class ChatDetailsFragment : BaseFragment() {

    lateinit var binding: FragmentChatDetailsBinding
    val authViewModel: AuthViewModel by viewModel()
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
            openDialog(R.id.homeBottomSheetFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        showLoaderDialog(false)
    }


}