package com.gvelesiani.passworx.ui.addPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.common.onTextChanged
import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.databinding.FragmentAddPasswordBinding
import me.ibrahimsn.lib.SmoothBottomBar


class AddPasswordFragment :
    BaseFragment<AddPasswordViewModel, FragmentAddPasswordBinding>(AddPasswordViewModel::class) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddPasswordBinding
        get() = FragmentAddPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        requireActivity().findViewById<SmoothBottomBar>(R.id.bottomBar).visibility = View.GONE
        setHasOptionsMenu(true)
        watchFields()
        setOnClickListeners()
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
                    label = etLabel.editText?.text.toString()
                )
            )
        }
        findNavController().navigateUp()
    }

    private fun watchFields() {
        binding.etTitle.editText?.onTextChanged {
            viewModel.onTitleChanged(it)
        }
        binding.etLabel.editText?.onTextChanged {
            viewModel.onLabelChanged(it)
        }
    }

    private fun observeViewState(viewState: AddPasswordViewModel.ViewState) {
        if (viewState.showAddNewPasswordError != null) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.generate_password_error_dialog_title))
                .setMessage(viewState.showAddNewPasswordError)
                .setPositiveButton(resources.getString(R.string.generate_password_error_dialog_button_text)) { _, _ ->
                }
                .show()
        }

        binding.etTitle.error = viewState.showTitleErrorMessage
        binding.etLabel.error = viewState.showLabelErrorMessage
        binding.btAddNewPassword.isEnabled = viewState.addButtonEnabled
    }

    override fun setupObservers() {
        viewModel.viewState.observe(this, {
            it?.let { observeViewState(it) }
        })
    }
}