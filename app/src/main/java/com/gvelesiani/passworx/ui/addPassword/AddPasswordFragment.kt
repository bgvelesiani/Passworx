package com.gvelesiani.passworx.ui.addPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.common.onTextChanged
import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.databinding.ActivityAddPasswordBinding


class AddPasswordFragment :
    BaseFragment<AddPasswordVM, ActivityAddPasswordBinding>(AddPasswordVM::class) {

    override fun setupView(savedInstanceState: Bundle?) {
        watchFields()
        setOnClickListeners()
        setupObservers()
    }

    private fun setOnClickListeners() {
        binding.btAddNewPassword.setOnClickListener {
            addNewPassword()
        }
    }

    private fun addNewPassword() {
        with(binding) {
            viewModel.addNewPassword(
                PasswordModel(
                    password = viewModel.encryptPassword(etPassword.editText?.text.toString()),
                    passwordTitle = etTitle.editText?.text.toString(),
                    websiteOrAppName = etWebsiteOrAppName.editText?.text.toString(),
                    emailOrUserName = etEmailOrUserName.editText?.text.toString(),
                    label = etGroup.editText?.text.toString()
                )
            )
        }
        findNavController().navigate(R.id.action_addNewPasswordFragment_to_viewPagerContainer)
    }

    private fun watchFields() {
        binding.etTitle.editText?.onTextChanged {
            viewModel.onTitleChanged(it)
        }
        binding.etGroup.editText?.onTextChanged {
            viewModel.onLabelChanged(it)
        }
    }

    private fun observeViewState(viewState: AddPasswordVM.ViewState) {
        if (viewState.showAddNewPasswordError != null) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.generate_password_error_dialog_title))
                .setMessage(viewState.showAddNewPasswordError)
                .setPositiveButton(resources.getString(R.string.generate_password_error_dialog_button_text)) { _, _ ->
                }
                .show()
        }

        binding.etTitle.error = viewState.showTitleErrorMessage
        binding.etGroup.error = viewState.showLabelErrorMessage
        binding.btAddNewPassword.isEnabled = viewState.addButtonEnabled
    }

    override fun setupObservers() {
        viewModel.viewState.observe(this) {
            it?.let { observeViewState(it) }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ActivityAddPasswordBinding =
        ActivityAddPasswordBinding::inflate
}