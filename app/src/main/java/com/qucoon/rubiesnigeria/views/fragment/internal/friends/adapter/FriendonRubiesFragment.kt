package com.qucoon.rubiesnigeria.views.fragment.internal.friends.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.base.BaseFragment
import com.qucoon.rubiesnigeria.base.observeChange
import com.qucoon.rubiesnigeria.databinding.FragmentFriendonRubiesBinding
import com.qucoon.rubiesnigeria.model.response.fetchfriends.Friend
import com.qucoon.rubiesnigeria.utils.getInitials
import com.qucoon.rubiesnigeria.utils.updateRecycler
import com.qucoon.rubiesnigeria.viewmodel.ChatViewModel
import com.qucoon.rubiesnigeria.viewmodel.RestAuthViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class FriendonRubiesFragment : BaseFragment() {
        private lateinit var binding: FragmentFriendonRubiesBinding
        private val restAuthViewModel: RestAuthViewModel by sharedViewModel()
        private val chatViewModel: ChatViewModel by sharedViewModel()
        private var listOfFriends: List<Friend>? = null


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentFriendonRubiesBinding.inflate(layoutInflater)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            observerView()

        }


        private fun observerView() {
            restAuthViewModel.getFriendsDB().observeChange(viewLifecycleOwner) { list ->
                listOfFriends = list
                setupRecycler(list)
            }
        }

        private fun setupRecycler(friendslists: List<Friend>) {
            binding.friendsRecycler.updateRecycler(
                requireContext(),
                friendslists,
                R.layout.friiends_blueprint,
                listOf(
                    R.id.initialsTextView_friend,
                    R.id.nameTextViewfriends,
                ),
                { innerView, position ->

                    val initialsTextView = innerView.get(R.id.initialsTextView_friend) as TextView
                    val nameTextView = innerView.get(R.id.nameTextViewfriends) as TextView


                    initialsTextView.setText(
                        friendslists.get(position).userName.trim().getInitials()
                    )
                    nameTextView.setText(friendslists.get(position).userName)
                },
                { position ->
                    openChatScreen(friendslists[position])
                })
        }

        private fun addFriendToList(position: Int) {
        }

        private fun openChatScreen(friend: Friend) {
            chatViewModel.friendObj.value = friend
            openFragment(R.id.chatScreenFragment)
        }

        override fun onResume() {
            super.onResume()
        }



    }

