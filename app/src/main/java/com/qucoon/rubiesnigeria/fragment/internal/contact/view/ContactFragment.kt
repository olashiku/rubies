package com.qucoon.rubiesnigeria.fragment.internal.contact.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.base.BaseFragment
import com.qucoon.rubiesnigeria.databinding.FragmentContactBinding
import com.qucoon.rubiesnigeria.utils.updateRecycler


class ContactFragment : BaseFragment() {

    private lateinit var binding: FragmentContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupClickListener()
    }

    private fun setupClickListener() {
        binding.bacButtonImage.setOnClickListener { popFragment() }
    }

    private fun String.getInitials(): String {
        val firstChar = this.split(" ")[0].first().uppercaseChar()
        val secondChar = this.split(" ")[1].first().uppercaseChar()
        return "$firstChar$secondChar"
    }

    private fun setupRecycler() {
        val mylist = resources.getStringArray(R.array.contacts_array).toList()
        binding.contactsRecycler.updateRecycler(
            requireContext(),
            mylist,
            R.layout.contacts_blueprint,
            listOf(R.id.initialsTextView, R.id.nameTextView),
            { innerView, position ->

                val initialsTextView = innerView.get(R.id.initialsTextView) as TextView
                val nameTextView = innerView.get(R.id.nameTextView) as TextView

                initialsTextView.setText(mylist.get(position).getInitials())
                nameTextView.setText(mylist.get(position))

            },
            { position ->
                openChatScreen()
            })
    }

    private fun openChatScreen() {
        openFragment(R.id.action_contactFragment_to_chatScreenFragment)
    }


}