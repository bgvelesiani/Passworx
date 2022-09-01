package com.gvelesiani.passworx.ui.editPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.common.onTextChanged
import com.gvelesiani.passworx.databinding.FragmentEditBinding
import com.gvelesiani.passworx.domain.model.PasswordModel

class UpdatePasswordFragment : BaseFragment<UpdatePasswordVM, FragmentEditBinding>(UpdatePasswordVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEditBinding
        get() = FragmentEditBinding::inflate

    private val args: UpdatePasswordFragmentArgs by navArgs()

    override fun setupView(savedInstanceState: Bundle?) {
        args.password?.let {
            setEditableData(it)
            viewModel.decryptPassword(password = it.password)
        }
        binding.toolbar.setupToolbar {
            findNavController().navigateUp()
        }
        watchFields()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btAddNewPassword.setOnClickListener {
            editPassword()
        }
    }

    override fun setupObservers() {
        viewModel.viewState.observe(this) {
            it?.let { observeViewState(it) }
        }
    }

    private fun editPassword() {
        with(binding) {
            viewModel.editPassword(
                PasswordModel(
                    passwordId = args.password?.passwordId!!,
                    password = viewModel.encryptPassword(etPassword.editText?.text.toString()),
                    passwordTitle = etTitle.editText?.text.toString(),
                    websiteOrAppName = etWebsiteOrAppName.editText?.text.toString(),
                    emailOrUserName = etEmailOrUserName.editText?.text.toString(),
                    label = etLabel.editText?.text.toString(),
                    isFavorite = args.password?.isFavorite!!
                )
            )
        }
        findNavController().navigateUp()
    }

    private fun observeViewState(viewState: UpdatePasswordVM.ViewState) {
        binding.etPassword.editText?.setText(viewState.password)
        if (viewState.showEditPasswordError != null) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.generate_password_error_dialog_title))
                .setMessage(viewState.showEditPasswordError)
                .setPositiveButton(resources.getString(R.string.generate_password_error_dialog_button_text)) { _, _ ->
                }
                .show()
        }

        binding.etTitle.error = viewState.showTitleErrorMessage
        binding.etLabel.error = viewState.showLabelErrorMessage
        binding.btAddNewPassword.isEnabled = viewState.addButtonEnabled
    }

    private fun setEditableData(password: PasswordModel) {
        with(binding) {
            etPassword.editText?.setText(password.password)
            etTitle.editText?.setText(password.passwordTitle)
            etWebsiteOrAppName.editText?.setText(password.websiteOrAppName)
            etEmailOrUserName.editText?.setText(password.emailOrUserName)
            etLabel.editText?.setText(password.label)
        }
    }

    private fun watchFields() {
        binding.etTitle.editText?.onTextChanged {
            viewModel.onTitleChanged(it)
        }
        binding.etLabel.editText?.onTextChanged {
            viewModel.onLabelChanged(it)
        }
    }
}