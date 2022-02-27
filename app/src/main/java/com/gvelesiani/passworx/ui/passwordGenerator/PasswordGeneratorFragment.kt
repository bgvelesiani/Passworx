package com.gvelesiani.passworx.ui.passwordGenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.constants.CLIP_DATA_PLAIN_TEXT_LABEL
import com.gvelesiani.passworx.databinding.FragmentPasswordGeneratorBinding
import me.ibrahimsn.lib.SmoothBottomBar


class PasswordGeneratorFragment :
    BaseFragment<PasswordGeneratorViewModel, FragmentPasswordGeneratorBinding>(
        PasswordGeneratorViewModel::class
    ) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPasswordGeneratorBinding
        get() = FragmentPasswordGeneratorBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        requireActivity().findViewById<SmoothBottomBar>(R.id.bottomBar).visibility = View.VISIBLE
        setUpListeners()
    }

    override fun setupObservers() {
        viewModel.generatedPassword.observe(viewLifecycleOwner, {
            binding.tvGeneratedPassword.text = it
        })

        viewModel.generatePasswordError.observe(viewLifecycleOwner, {
            // TODO: 2/22/2022 Research for more intuitive design
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.generate_password_error_dialog_title))
                .setMessage(it)
                .setPositiveButton(resources.getString(R.string.generate_password_error_dialog_button_text)) { _, _ ->
                }
                .show()
        })
    }

    private fun setUpListeners() {
        with(binding) {
            sbPasswordGenerator.addOnChangeListener { _, value, _ ->
                viewModel.generatePassword(value.toInt())
            }
            btCopyGeneratedPassword.setOnClickListener {
                copyTextToClipboard(tvGeneratedPassword.text.toString())
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
        }
    }


    private fun copyTextToClipboard(generatedPassword: String) {
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(CLIP_DATA_PLAIN_TEXT_LABEL, generatedPassword)
        clipboard.setPrimaryClip(clip)
        setUpAndShowSnackBar()
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