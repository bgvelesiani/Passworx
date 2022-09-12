package com.gvelesiani.passworx.ui.passwordDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.gvelesiani.domain.model.PasswordModel
import com.gvelesiani.passworx.databinding.BottomSheetPasswordDetailsBinding
import com.gvelesiani.passworx.ui.passwords.PasswordsFragmentDirections

class PasswordDetailsBottomSheet :
    com.gvelesiani.base.BaseBottomSheet<PasswordDetailsVM, BottomSheetPasswordDetailsBinding>(
        PasswordDetailsVM::class
    ) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetPasswordDetailsBinding
        get() = BottomSheetPasswordDetailsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        val password = arguments?.getParcelable<PasswordModel>(PASSWORD)
        if (password != null) {
            setData(password)
            viewModel.decryptPassword(password = password.password)
            setOnClickListeners(password)
        }
    }

    override fun setupObservers() {
        viewModel.viewState.observe(this) {
            it?.let { observeViewState(it) }
        }
    }

    private fun setOnClickListeners(password: PasswordModel) {
        binding.btEditPassword.setOnClickListener {
            findNavController().navigate(
                PasswordsFragmentDirections.actionNavigationPasswordsToUpdatePasswordFragment(
                    password
                )
            )
            this.dismiss()
        }
    }

    private fun setData(password: PasswordModel) {
        with(binding) {
            tvPassword.editText?.isFocusable = false
            tvEmailOrUsername.text = password.emailOrUserName
            tvWebsiteOrAppName.text = password.websiteOrAppName
            tvPasswordName.text = password.passwordTitle
        }
    }

    private fun observeViewState(viewState: PasswordDetailsVM.ViewState) {
        binding.tvPassword.editText?.setText(viewState.password)
    }

    companion object {
        const val TAG = "PasswordDetailsBottomSheet"
        private const val PASSWORD = "PASSWORD"
        fun show(
            password: PasswordModel,
            fragmentManager: FragmentManager,
            tag: String
        ) {
            PasswordDetailsBottomSheet().apply {
                arguments = bundleOf(PASSWORD to password)
                show(fragmentManager, tag)
            }
        }
    }
}