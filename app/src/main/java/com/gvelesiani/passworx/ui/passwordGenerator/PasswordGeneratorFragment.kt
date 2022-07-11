package com.gvelesiani.passworx.ui.passwordGenerator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.common.copyToClipboard
import com.gvelesiani.passworx.databinding.FragmentPasswordGeneratorBinding


class PasswordGeneratorFragment :
    BaseFragment<PasswordGeneratorVM, FragmentPasswordGeneratorBinding>(
        PasswordGeneratorVM::class
    ) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPasswordGeneratorBinding
        get() = FragmentPasswordGeneratorBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setUpListeners()
    }

    override fun setupObservers() {
        viewModel.generatedPassword.observe(viewLifecycleOwner) {
            binding.tvGeneratedPassword.text = it
        }

        viewModel.generatePasswordError.observe(viewLifecycleOwner) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.generate_password_error_dialog_title))
                .setMessage(it)
                .setPositiveButton(resources.getString(R.string.generate_password_error_dialog_button_text)) { _, _ ->
                }
                .show()
        }
    }

    private fun setUpListeners() {
        with(binding) {
            sbPasswordGenerator.addOnChangeListener { _, value, _ ->
                viewModel.generatePassword(value.toInt())
            }
            btCopyGeneratedPassword.setOnClickListener {
                tvGeneratedPassword.text.toString().copyToClipboard(requireContext())
                setUpAndShowSnackBar()
            }
            smUseCapitalLetters.setOnCheckedChangeListener { _, isChecked ->
                viewModel.useCapitalLetters(isChecked, sbPasswordGenerator.value.toInt())
            }
            smUseNumbers.setOnCheckedChangeListener { _, isChecked ->
                viewModel.useNumbers(isChecked, sbPasswordGenerator.value.toInt())
            }
            smUseSymbols.setOnCheckedChangeListener { _, isChecked ->
                viewModel.useSymbols(isChecked, sbPasswordGenerator.value.toInt())
            }
            binding.backClickArea.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setUpAndShowSnackBar() {
        val snackBar = Snackbar.make(
            binding.root,
            getString(R.string.copy_password_snackbar_text),
            Snackbar.LENGTH_SHORT
        )
        snackBar.anchorView = binding.btCopyGeneratedPassword
        snackBar.show()
    }
}