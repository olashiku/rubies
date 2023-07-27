package com.qucoon.rubiesnigeria.views.fragment.internal.chat_screen.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.base.BaseFragment
import com.qucoon.rubiesnigeria.databinding.FragmentChatBinding
import com.qucoon.rubiesnigeria.model.chat.Chat
import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.storage.getStringPref
import com.qucoon.rubiesnigeria.utils.*
import com.qucoon.rubiesnigeria.viewmodel.ChatViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class ChatScreenFragment : BaseFragment() {

    lateinit var binding: FragmentChatBinding
    val chatViewModel: ChatViewModel by viewModel()
    private val contact: Contactslist by argument("contact")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        setupContact()
        setupObserver()
        getKeyPressed()
    }

    private fun setupObserver() {
        chatViewModel.getChatByRecipient(contact.phonenumber).observe(viewLifecycleOwner) {
            setupChatRecycler(it)
        }
    }

    private fun setupContact() {
        binding.contactNameTextView.setText(contact.name)
    }

    private fun setupChatRecycler(chats: List<Chat>) {
        println("chats ${chats.map { it.sender }} \n")
        binding.chatRecycler.updateChatRecycler(requireContext(),
            chats, R.layout.chat_layout, listOf(
                R.id.recieverMainConstraintLayout,
                R.id.autoTextView,
                R.id.receiverMessageTextView,
                R.id.autoImageView,
                R.id.recieversTimeTextView,
                R.id.senderMainConstraintLayout,
                R.id.senderConstraintLayout,
                R.id.senderMessageTextView,
                R.id.senderTimeTextView
            ), { view, position ->

                // receiver  chat details
                val receiverMainConstraintLayout =
                    view.get(R.id.recieverMainConstraintLayout) as ConstraintLayout
                val autoTextView = view.get(R.id.autoTextView) as TextView
                val receiverMessageTextView = view.get(R.id.receiverMessageTextView) as TextView
                val autoImageView = view.get(R.id.autoImageView) as ImageView
                val receiverTimeTextView = view.get(R.id.recieversTimeTextView) as TextView

                // sender chat details
                val senderMainConstraintLayout =
                    view.get(R.id.senderMainConstraintLayout) as ConstraintLayout
                val senderConstraintLayout = view.get(R.id.senderConstraintLayout) as ConstraintLayout
                val senderMessageTextView = view.get(R.id.senderMessageTextView) as TextView
                val senderTimeTextView = view.get(R.id.senderTimeTextView) as TextView


                if (chats.get(position).sender.equals(paperPrefs.getStringPref(PaperPrefs.USER_PHONE))) {
                    receiverMainConstraintLayout.hide()
                    senderMainConstraintLayout.show()
                    senderMessageTextView.setText(chats.get(position).message)
                } else {
                    receiverMainConstraintLayout.show()
                    senderMainConstraintLayout.hide()
                    receiverMessageTextView.setText(chats.get(position).message)
                }
            }, { position -> })

    }

    private fun setupClickListener() {
        binding.sendButton.setOnClickListener {
            messagingAction()
        }
    }

    private fun messagingAction() {
        if (checkEmptyInput()) {
            chatViewModel.sendMessages(binding.editTextTextChat.getString(),contact.phonenumber,paperPrefs.getStringPref(PaperPrefs.USER_PHONE))
            binding.editTextTextChat.text.clear()
        }
    }

    private fun checkEmptyInput(): Boolean {
        return binding.editTextTextChat.text.isNotEmpty()
    }

    private fun getKeyPressed() {
        binding.editTextTextChat.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                messagingAction()
                return@OnKeyListener true
            }
            false
        })
    }

    override fun onResume() {
        super.onResume()
        showLoaderDialog(false)
    }
}