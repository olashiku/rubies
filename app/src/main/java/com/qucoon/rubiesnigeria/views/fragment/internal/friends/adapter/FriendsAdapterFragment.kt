package com.qucoon.rubiesnigeria.views.fragment.internal.friends.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.views.fragment.internal.contact.view.ContactFragment


class FriendsAdapterFragment (fragmentManager: FragmentManager, lifecycle : Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int {
            return 2
        }
        override fun createFragment(position: Int): Fragment {
            return when (position){
                0 -> {
                    FriendonRubiesFragment()
                }

                1 -> {
                    ContactFragment()
                }

                else -> {
                    FriendonRubiesFragment()
                }
            }
        }


    }