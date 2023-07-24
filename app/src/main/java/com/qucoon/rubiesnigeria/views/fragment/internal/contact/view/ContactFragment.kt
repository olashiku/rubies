package com.qucoon.rubiesnigeria.views.fragment.internal.contact.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.base.BaseFragment
import com.qucoon.rubiesnigeria.databinding.FragmentContactBinding
import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.utils.Constant
import com.qucoon.rubiesnigeria.utils.cleanContact
import com.qucoon.rubiesnigeria.utils.getInitials
import com.qucoon.rubiesnigeria.utils.updateRecycler
import com.qucoon.rubiesnigeria.viewmodel.AuthViewModel
import com.qucoon.rubiesnigeria.viewmodel.ChatViewModel
import org.koin.android.ext.android.inject


class ContactFragment : BaseFragment() {

    private lateinit var binding: FragmentContactBinding
    val chatViewModel: ChatViewModel by inject()
    val authViewModel: AuthViewModel by inject()

    var contactslists: List<Contactslist> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver(authViewModel)
        setupObserver(chatViewModel)
        setupClickListener()
        getLiveContacts()
        setupObserver()
    }


     private fun setupObserver(){
         authViewModel.addFriendResponse.observe(viewLifecycleOwner){
             // TODO: perform action here.
         }

     }

    private fun getLiveContacts() {
        chatViewModel.getContacts().observe(viewLifecycleOwner) {
            val cleanContacts = cleanContact(it)
            setupRecycler(cleanContacts)
        }
    }

    private fun setupClickListener() {
        binding.bacButtonImage.setOnClickListener { popFragment() }
    }


    private fun setupRecycler(contactslists: List<Contactslist>) {
        this.contactslists = contactslists
        binding.contactsRecycler.updateRecycler(
            requireContext(),
            contactslists,
            R.layout.contacts_blueprint,
            listOf(
                R.id.initialsTextView,
                R.id.nameTextView,
                R.id.actionButton,
                R.id.contactTypeTextView
            ),
            { innerView, position ->

                val initialsTextView = innerView.get(R.id.initialsTextView) as TextView
                val nameTextView = innerView.get(R.id.nameTextView) as TextView
                val contactTypeTextView = innerView.get(R.id.contactTypeTextView) as TextView
                val actionButton = innerView.get(R.id.actionButton) as Button

                initialsTextView.setText(contactslists.get(position).name.trim().getInitials())
                nameTextView.setText(contactslists.get(position).name)

                if (contactslists.get(position).isFriend.equals(Constant.no)) {
                    contactTypeTextView.setText(getString(R.string.add_friend))
                } else {
                    contactTypeTextView.setText(getString(R.string.message_friend))
                }

                actionButton.setOnClickListener {
                    if (contactslists.get(position).isFriend.equals(Constant.no)) {
                        addFriendToList(position)
                    } else {
                        openChatScreen()
                    }
                }
            },
            { position -> })
    }

    private fun addFriendToList(position: Int) {
        makeActiveCalls {
            chatViewModel.addFriends(contactslists.get(position))
        }
    }

    private fun openChatScreen() {
        openFragment(R.id.action_contactFragment_to_chatScreenFragment)
    }


}