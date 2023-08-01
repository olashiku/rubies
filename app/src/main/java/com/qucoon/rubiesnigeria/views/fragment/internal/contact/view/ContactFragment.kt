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
import com.qucoon.rubiesnigeria.model.response.fetchfriends.Friend
import com.qucoon.rubiesnigeria.utils.*
import com.qucoon.rubiesnigeria.viewmodel.AuthViewModel
import com.qucoon.rubiesnigeria.viewmodel.ChatViewModel
import com.qucoon.rubiesnigeria.viewmodel.RestAuthViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel


class ContactFragment : BaseFragment() {

    private lateinit var binding: FragmentContactBinding
    val chatViewModel: ChatViewModel by inject()
    private val restAuthViewModel: RestAuthViewModel by sharedViewModel()
    val authViewModel: AuthViewModel by inject()
    lateinit var selectedContact : Contactslist
    var liveContactList = mutableListOf<Contactslist>()
    var contactslists: List<Contactslist> = emptyList()
    private var listOfFriends: List<Friend>? = null

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
        getLiveContacts()
        setupClickListener()
        initView()
    }


     private fun initView(){
         triggerView(true)
     }

     private fun triggerView(status:Boolean){
         if(status){
             binding.gettingContactsText.show()
             binding.swipeToRefresh.isRefreshing = true

         }else{
             binding.swipeToRefresh.isRefreshing = false
             binding.gettingContactsText.hide()

         }
     }

    private fun getLiveContacts() {
        chatViewModel.getContacts().observe(viewLifecycleOwner) {
            val myList =  regularizeContacts(it as MutableList<Contactslist>)
            if(myList.isEmpty()){ triggerView(true)} else { triggerView(false)}
            setupRecycler(myList)
        }

    }

     private fun regularizeContacts(contact: MutableList<Contactslist>):List<Contactslist>{
     return   contact.sortedByDescending { it.isFriend }
     }

    private fun setupClickListener() {
      binding.bacButtonImage.setOnClickListener { popFragment() }
    }

//  I  i_have_been_called 3914
//  I  i_have_been_called 3914

    private fun setupRecycler(contactslists: List<Contactslist>) {
        println("activeContactList_total ${contactslists.size}")
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
                        selectedContact = contactslists.get(position)
                        addFriendToList(position)
                    } else {
                        openChatScreen(contactslists.get(position))
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

    private fun openChatScreen(contact: Contactslist) {
        openFragmentWithData(R.id.chatScreenFragment,"contact" to contact)
    }

    override fun onResume() {
        super.onResume()
        showLoaderDialog(false)
    }

}