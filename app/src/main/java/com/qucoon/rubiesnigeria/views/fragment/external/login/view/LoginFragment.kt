package com.qucoon.rubiesnigeria.views.fragment.external.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.base.BaseFragment
import com.qucoon.rubiesnigeria.databinding.FragmentLoginBinding
import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.utils.Validator
import com.qucoon.rubiesnigeria.viewmodel.AuthViewModel
import com.qucoon.rubiesnigeria.views.activity.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  setupObserver(authViewModel)
        setupObserver()
        setupClickListener()
        observeInputText()
        getContacts()

        authViewModel.showLoading.observe(viewLifecycleOwner){
            println("loading_status $it")
        }
    }

    private fun observeInputText() {
        binding.phoneNumberEditText.doAfterTextChanged {
            validateData()
        }
    }

    private fun validateData() {
        checkValidationStatus(Validator.isValidPhone(binding.phoneNumberEditText, true))
    }


    private fun checkValidationStatus(
        isPhoneCodeFilled: Boolean,
    ) {
        if (isPhoneCodeFilled) {
            binding.loginButton.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.black)
            binding.loginButton.isEnabled = true
        } else {
            binding.loginButton.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.off_white)
            binding.loginButton.isEnabled = false
        }
    }

    private fun setupClickListener() {
        binding.loginButton.setOnClickListener {
            openFragment(R.id.action_loginFragment_to_navigation)

//            makeActiveCalls {
//                authViewModel.login( binding.phoneNumberEditText.text.toString())
//            }
        }

        binding.registerButton.setOnClickListener {
            openFragment(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setupObserver() {
        authViewModel.loginResponse.observe(viewLifecycleOwner) {
            openFragment(R.id.action_loginFragment_to_navigation)
        }
    }


}