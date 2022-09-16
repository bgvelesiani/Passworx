package com.gvelesiani.passworx.ui.passwords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.snackbar.Snackbar
import com.gvelesiani.base.BaseFragment
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.common.extensions.copyToClipboard
import com.gvelesiani.passworx.common.extensions.hideKeyboard
import com.gvelesiani.passworx.databinding.FragmentPasswordsBinding


@Deprecated("Needs to be deleted soon")
class PasswordsFragment :
    BaseFragment<PasswordsVM, FragmentPasswordsBinding>(PasswordsVM::class) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPasswordsBinding
        get() = FragmentPasswordsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        hideKeyboard()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun setupObservers() {
        viewModel.decryptedPassword.observe(viewLifecycleOwner) {
            it.copyToClipboard(requireContext())
            val snackbar = Snackbar.make(
                requireView(),
                getString(R.string.password_copying_success),
                Snackbar.LENGTH_SHORT
            )
            snackbar.show()
        }
    }
}