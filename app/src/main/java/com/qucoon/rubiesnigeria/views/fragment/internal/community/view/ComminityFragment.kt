package com.qucoon.rubiesnigeria.views.fragment.internal.community.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qucoon.rubiesnigeria.base.BaseFragment
import com.qucoon.rubiesnigeria.databinding.FragmentComminityBinding


class ComminityFragment : BaseFragment()  {

    private lateinit var bindingFragmentComminityFragment: FragmentComminityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingFragmentComminityFragment = FragmentComminityBinding.inflate(layoutInflater)
        return bindingFragmentComminityFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupClickListener()
    }

    private fun  setupView(){
        showBottomNavigation(true)
    }

     private fun setupClickListener(){

     }

    override fun onResume() {
        super.onResume()
        showLoaderDialog(false)
    }

}