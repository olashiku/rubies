package com.qucoon.rubiesnigeria.fragment.external

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.qucoon.rubiesnigeria.AuthenticationViewModel
import com.qucoon.rubiesnigeria.BuildConfig
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.base.BaseFragment
import com.qucoon.rubiesnigeria.databinding.FragmentSplashScreenBinding
import com.qucoon.rubiesnigeria.utils.Utils


class SplashScreenFragment : BaseFragment() {
    lateinit var binding: FragmentSplashScreenBinding
    private val viewModel: AuthenticationViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextView()
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        Utils.delayTimer {
            if (viewModel.firstTimeLogin) {
                openFragment(R.id.action_splashScreenFragment_to_registerFragment)
            } else {
                openFragment(R.id.action_splashScreenFragment_to_navigation)
            }
        }

    }



    private fun setupTextView() {
        binding.versionText.text = getString(R.string.version_text, BuildConfig.VERSION_NAME)
    }

}