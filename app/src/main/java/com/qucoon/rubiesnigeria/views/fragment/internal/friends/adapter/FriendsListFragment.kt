package com.qucoon.rubiesnigeria.views.fragment.internal.friends.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.base.BaseFragment
import com.qucoon.rubiesnigeria.databinding.FragmentContactBinding
import com.qucoon.rubiesnigeria.databinding.FragmentFriendsListBinding

class FriendsListFragment : BaseFragment() {
    private lateinit var binding: FragmentFriendsListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendsListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        val adapter = FriendsAdapterFragment(getChildFragmentManager(), lifecycle)

        binding.dashboardViewpager.adapter = adapter

        TabLayoutMediator(binding.dashboardTablayout, binding.dashboardViewpager){ tab, position ->

            when (position) {

                0 -> {
                    tab.text = "Friends on Rubies"
                }

                1 -> {
                    tab.text = "Contacts List"
                }

            }
        }.attach()
    }

    private fun setupClickListener() {
        binding.bacButtonImage.setOnClickListener { popFragment() }
    }

//    override fun onResume() {
//        super.onResume()
//        socketViewModel.fetchFriends()
//    }

}