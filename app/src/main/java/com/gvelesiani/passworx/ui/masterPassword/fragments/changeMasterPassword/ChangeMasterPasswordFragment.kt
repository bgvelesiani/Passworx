package com.gvelesiani.passworx.ui.masterPassword.fragments.changeMasterPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.common.onTextChanged
import com.gvelesiani.passworx.databinding.FragmentChangeMasterPasswordBinding
import com.gvelesiani.passworx.helpers.validateMasterPassword.MasterPasswordValidatorHelperImpl

class ChangeMasterPasswordFragment :
    BaseFragment<ChangeMasterPasswordVM, FragmentChangeMasterPasswordBinding>(
        ChangeMasterPasswordVM::class
    ) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChangeMasterPasswordBinding =
        FragmentChangeMasterPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        binding.toolbar.setupToolbar {
            findNavController().navigateUp()
        }
        watchField()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btChangeMasterPassword.setOnClickListener {
            viewModel.validate(
                binding.etCurrentMasterPassword.editText?.text.toString(),
                binding.etNewMasterPassword.editText?.text.toString()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        /**
         * Clearing check on chips because while navigating back and forth
         *  chips were staying checked.
         *  */
        binding.masterPasswordChips.clearCheck()
    }

    override fun setupObservers() {
        viewModel.viewState.observe(this) {
            if (it.validationErrors != null) {
                onValidationErorr(it.validationErrors)
            }
            binding.btChangeMasterPassword.isEnabled = it.isValid
            binding.etCurrentMasterPassword.error = it.showPasswordVerifyError

            if (it.changeSuccess) {
                findNavController().navigateUp()
                val snackbar = Snackbar.make(
                    requireView(),
                    getString(R.string.master_password_change_success_message),
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }

        }
    }

    private fun onValidationErorr(errors: List<MasterPasswordValidatorHelperImpl.MasterPasswordError>) {
        with(binding) {
            chipLength.text = errors[0].error
            chipLength.isChecked = errors[0].isValid

            chipNumber.text = errors[1].error
            chipNumber.isChecked = errors[1].isValid

            chipCapital.text = errors[2].error
            chipCapital.isChecked = errors[2].isValid

            chipSmall.text = errors[3].error
            chipSmall.isChecked = errors[3].isValid

            chipSymbol.text = errors[4].error
            chipSymbol.isChecked = errors[4].isValid
        }
    }

    private fun watchField() {
        binding.etNewMasterPassword.editText?.onTextChanged {
            viewModel.validateNewPassword(it)
        }
    }

    companion object {
        const val TAG = "MasterPasswordDetailsBottomSheet"
    }
}