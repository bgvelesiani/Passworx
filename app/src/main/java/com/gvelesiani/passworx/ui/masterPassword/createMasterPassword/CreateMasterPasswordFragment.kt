package com.gvelesiani.passworx.ui.masterPassword.createMasterPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.common.onTextChanged
import com.gvelesiani.passworx.databinding.FragmentCreateMasterPasswordBinding
import com.gvelesiani.passworx.helpers.validateMasterPassword.MasterPasswordValidatorHelperImpl

class CreateMasterPasswordFragment :
    BaseFragment<CreateMasterPasswordVM, FragmentCreateMasterPasswordBinding>(
        CreateMasterPasswordVM::class
    ) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCreateMasterPasswordBinding =
        FragmentCreateMasterPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        watchField()
        binding.btCreateMasterPassword.setOnClickListener {
            viewModel.createMasterPassword(binding.etMasterPassword.editText?.text.toString())
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
        with(binding) {
            viewModel.viewState.observe(viewLifecycleOwner) {
                if (it.showCreateMasterPasswordError != null) {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(resources.getString(R.string.generate_password_error_dialog_title))
                        .setMessage(it.showCreateMasterPasswordError)
                        .setPositiveButton(resources.getString(R.string.generate_password_error_dialog_button_text)) { _, _ ->
                        }
                        .show()
                }

                if (it.validationErrors != null) {
                    onValidationErorr(it.validationErrors)
                }

                btCreateMasterPassword.isEnabled = it.isValid

                if (it.validationSuccess) {
                    findNavController().navigateUp()
                    val snackbar = Snackbar.make(
                        requireView(),
                        getString(R.string.master_password_creation_success_message),
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.anchorView =
                        activity?.findViewById<BottomNavigationView>(R.id.bottomBar)
                    snackbar.show()
                }
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
        binding.etMasterPassword.editText?.onTextChanged {
            viewModel.validate(it)
        }
    }
}