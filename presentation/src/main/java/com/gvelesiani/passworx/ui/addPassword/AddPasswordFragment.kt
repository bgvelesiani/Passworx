package com.gvelesiani.passworx.ui.addPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gvelesiani.base.BaseFragment
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.databinding.FragmentAddPasswordBinding


class AddPasswordFragment :
    BaseFragment<AddPasswordVM, FragmentAddPasswordBinding>(AddPasswordVM::class) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddPasswordBinding =
        FragmentAddPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
//        binding.toolbar.setupToolbar {
//            findNavController().navigateUp()
//        }
//        watchFields()
        setOnClickListeners()
        binding.content.setContent {
            AddPasswordScreen(
//                viewModel = viewModel,
                navController = findNavController()
            )
        }
    }

    private fun setOnClickListeners() {
//        binding.btAddNewPassword.setOnClickListener {
//            addNewPassword()
//        }
    }

//    private fun watchFields() {
//        binding.etTitle.editText?.onTextChanged {
//            viewModel.onTitleChanged(it)
//        }
//        binding.etLabel.editText?.onTextChanged {
//            viewModel.onLabelChanged(it)
//        }
//    }

    @Preview
    @Composable
    fun AddPasswordContentPreview() {
        AddPasswordScreen(
            findNavController())
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
    }

    override fun setupObservers() {
        viewModel.viewState.observe(this) {
            it?.let { observeViewState(it) }
        }
    }
}