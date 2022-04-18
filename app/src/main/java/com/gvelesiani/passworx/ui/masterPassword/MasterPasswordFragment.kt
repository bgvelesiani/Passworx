package com.gvelesiani.passworx.ui.masterPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.databinding.FragmentMasterPasswordBinding

class MasterPasswordFragment :
    BaseFragment<MasterPasswordVM, FragmentMasterPasswordBinding>(MasterPasswordVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMasterPasswordBinding =
        FragmentMasterPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btGoToVault.setOnClickListener {
            viewModel.doesPasswordMatch(binding.etMasterPassword.editText?.text.toString())
        }
    }

    override fun setupObservers() {
        viewModel.viewState.observe(this) {
            if (it.passwordMatchError != null) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.generate_password_error_dialog_title))
                    .setMessage(it.passwordMatchError)
                    .setPositiveButton(resources.getString(R.string.generate_password_error_dialog_button_text)) { _, _ ->
                    }
                    .show()
            }
            if (it.passwordMatches) {
                findNavController().navigate(R.id.action_masterPasswordFragment_to_navigation_passwords)
            }
        }
    }
}