package com.gvelesiani.passworx.ui.masterPassword.createMasterPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.databinding.FragmentCreateMasterPasswordBinding

class CreateMasterPasswordFragment :
    BaseFragment<CreateMasterPasswordVM, FragmentCreateMasterPasswordBinding>(CreateMasterPasswordVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCreateMasterPasswordBinding =
        FragmentCreateMasterPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        binding.btCreateMasterPassword.setOnClickListener {
            viewModel.createMasterPassword(binding.etMasterPassword.editText?.text.toString())
            findNavController().navigateUp()
        }
    }

    override fun setupObservers() {
        viewModel.viewState.observe(this, {
            if (it.showCreateMasterPasswordError != null) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.generate_password_error_dialog_title))
                    .setMessage(it.showCreateMasterPasswordError)
                    .setPositiveButton(resources.getString(R.string.generate_password_error_dialog_button_text)) { _, _ ->
                    }
                    .show()
            }
        })
    }
}