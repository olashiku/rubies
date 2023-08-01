package com.qucoon.rubiesnigeria.views.fragment.internal.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.databinding.FragmentHomeBottomSheetBinding
import com.qucoon.rubiesnigeria.utils.updateRecycler
import com.qucoon.rubiesnigeria.viewmodel.AuthViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


    class HomeBottomSheetFragment : BottomSheetDialogFragment() {
        private lateinit var binding: FragmentHomeBottomSheetBinding
        private val authViewModel: AuthViewModel by sharedViewModel()
        data class HomeData(
            val homeIcon: Int,
            val title: String,
            val body: String
        )

        private val listOfHomeData = listOf(
            HomeData(
                R.drawable.add_contact,
                "Friend",
                "Add a friend directly on Rubies"
            ),
            HomeData(
                R.drawable.bills_image,
                "Business",
                "Pay all your bills on Rubies"
            )
        )

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentHomeBottomSheetBinding.inflate(layoutInflater)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            updateUI()

        }

        private fun updateUI() {
            binding.homeRecycler.updateRecycler(requireContext(), listOfHomeData,
                R.layout.item_home,
                listOf(
                    R.id.imageView18,
                    R.id.textView7Tittle,
                    R.id.textView9Body
                ),
                { innerViews, position ->
                    val icon = innerViews[R.id.imageView18] as ImageView
                    val tittle = innerViews[R.id.textView7Tittle] as TextView
                    val body = innerViews[R.id.textView9Body] as TextView


                    icon.setImageResource(listOfHomeData[position].homeIcon)
                    tittle.text = listOfHomeData[position].title
                    body.text = listOfHomeData[position].body


                },
                { position ->

                    when (listOfHomeData[position].title) {
                        "Friend" -> {
                            dialog?.dismiss()
                            findNavController().navigate(R.id.contactFragment)
                        }
                    }

                    when (listOfHomeData[position].title) {
                        "Business" -> {
                            dialog?.dismiss()
                        }

                    }
                })
        }

}