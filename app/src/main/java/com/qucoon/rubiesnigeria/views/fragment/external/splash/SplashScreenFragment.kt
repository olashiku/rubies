package com.qucoon.rubiesnigeria.views.fragment.external.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.qucoon.rubiesnigeria.BuildConfig
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.base.BaseFragment
import com.qucoon.rubiesnigeria.databinding.FragmentSplashScreenBinding
import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.utils.Utils


class SplashScreenFragment : BaseFragment() {
    lateinit var binding: FragmentSplashScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavigation(false)
        setupTextView()
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        Utils.delayTimer {
            launchScreen()
        }
    }

    private fun launchScreen(){
         lifecycleScope.launchWhenResumed {
             if( paperPrefs.getBooleanFromPref(PaperPrefs.HAS_ENROLLED)){
                 openFragment(R.id.action_splashScreenFragment_to_navigation)

             }else {
                 openFragment(R.id.action_splashScreenFragment_to_loginFragment)
             }
         }
     }

    private fun setupTextView() {
        binding.versionText.text = getString(R.string.version_text, BuildConfig.VERSION_NAME)
    }

}