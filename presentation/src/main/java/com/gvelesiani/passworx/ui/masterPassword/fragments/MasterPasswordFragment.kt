package com.gvelesiani.passworx.ui.masterPassword.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gvelesiani.base.BaseFragment
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.databinding.FragmentMasterPasswordBinding
import com.gvelesiani.passworx.ui.MainActivity

class MasterPasswordFragment :
    BaseFragment<MasterPasswordVM, FragmentMasterPasswordBinding>(
        MasterPasswordVM::class
    ) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMasterPasswordBinding =
        FragmentMasterPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btGoToVault.setOnClickListener {
            viewModel.doesPasswordMatch(binding.etMasterPassword.editText?.text.toString())
        }
        binding.btFingerprintAuth.setOnClickListener {
            setupBiometrics()
        }
    }

    private fun setupBiometrics() {
        with(viewModel.getBiometrics()) {
            setupBiometricPrompt(this@MasterPasswordFragment, requireContext()) {
                if (it) {
                    requireActivity().finish()
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                }
            }
            authenticate()
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
                requireActivity().finish()
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
            binding.btFingerprintAuth.isVisible = it.biometricsAreAllowed
        }
    }
}