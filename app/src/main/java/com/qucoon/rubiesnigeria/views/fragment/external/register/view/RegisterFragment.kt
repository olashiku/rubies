package com.qucoon.rubiesnigeria.views.fragment.external.register.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.base.BaseFragment
import com.qucoon.rubiesnigeria.databinding.FragmentRegisterBinding
import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.storage.savePref
import com.qucoon.rubiesnigeria.utils.Constant
import com.qucoon.rubiesnigeria.utils.Validator
import com.qucoon.rubiesnigeria.viewmodel.AuthViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         setupObserver(authViewModel)
        setupObserver()
        showBottomNavigation(false)
        setupClickListener()
        observeInputText()
    }



     private fun setupObserver(){
         authViewModel.signupResponse.observe(viewLifecycleOwner){ response ->
             paperPrefs.savePref(PaperPrefs.HAS_ENROLLED,true)
             openFragment(R.id.action_registerFragment_to_navigation)
         }

         authViewModel.errorMessage.observe(viewLifecycleOwner){
             showToast(it)
         }
     }

    private fun observeInputText() {
        binding.phoneNumberEditText.doAfterTextChanged {
            validateData()
        }

        binding.firstNameEditText.doAfterTextChanged {
            validateData()
        }

        binding.lastNameEditText.doAfterTextChanged {
            validateData()
        }
    }


    private fun validateData() {
        checkValidationStatus(
            Validator.isValidFirstName(binding.firstNameEditText, true),
            Validator.isValidLastName(binding.lastNameEditText, true),
            Validator.isValidPhone(binding.phoneNumberEditText, true)
        )
    }

    private fun checkValidationStatus(
        isPhoneValid: Boolean,
        isFirstNameValid: Boolean,
        isLastNameValid: Boolean
    ) {
        if (isPhoneValid && isFirstNameValid && isLastNameValid) {
            binding.proceedButton.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.black)
            binding.proceedButton.isEnabled = true
        } else {
            binding.proceedButton.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.off_white)
            binding.proceedButton.isEnabled = false
        }
    }

    private fun setupClickListener() {
        binding.proceedButton.setOnClickListener {
            makeActiveCalls {
                showLoaderDialog(true)
                authViewModel.register(
                    binding.phoneNumberEditText.text.toString(),
                    binding.firstNameEditText.text.toString(),
                    binding.phoneNumberEditText.text.toString()
                )
            }
        }

        binding.backButton.setOnClickListener { popFragment() }
    }

}