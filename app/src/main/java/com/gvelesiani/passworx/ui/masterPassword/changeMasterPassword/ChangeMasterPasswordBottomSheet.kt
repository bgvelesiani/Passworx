package com.gvelesiani.passworx.ui.masterPassword.changeMasterPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseBottomSheet
import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.databinding.BottomSheetChangeMasterPasswordBinding
import com.gvelesiani.passworx.ui.passwordDetails.PasswordDetailsBottomSheet

class ChangeMasterPasswordBottomSheet :
    BaseBottomSheet<ChangeMasterPasswordVM, BottomSheetChangeMasterPasswordBinding>(ChangeMasterPasswordVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetChangeMasterPasswordBinding =
        BottomSheetChangeMasterPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        binding.btChangeMasterPassword.setOnClickListener {
//            viewModel.createMasterPassword(binding.etMasterPassword.editText?.text.toString())
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

    companion object {
        const val TAG = "PasswordDetailsBottomSheet"
        private const val MASTER_PASSWORD = "PASSWORD"
        fun show(
//            password: String,
            fragmentManager: FragmentManager,
            tag: String
        ) {
            ChangeMasterPasswordBottomSheet().apply {
//                arguments = bundleOf(MASTER_PASSWORD to password)
                show(fragmentManager, tag)
            }
        }
    }
}