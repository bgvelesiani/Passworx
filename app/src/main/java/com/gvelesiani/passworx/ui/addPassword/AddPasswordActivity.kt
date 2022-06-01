package com.gvelesiani.passworx.ui.addPassword

import android.content.Intent
import android.content.Intent.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import com.google.android.material.color.MaterialColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gvelesiani.passworx.MainActivity
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseActivity
import com.gvelesiani.passworx.common.onTextChanged
import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.databinding.ActivityAddPasswordBinding


class AddPasswordActivity: BaseActivity<AddPasswordVM, ActivityAddPasswordBinding>(AddPasswordVM::class) {
    override val bindingInflater: (LayoutInflater) -> ActivityAddPasswordBinding
        get() = ActivityAddPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setBackgroundToActionBar()
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
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
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        finish()
        startActivity(intent)
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
            MaterialAlertDialogBuilder(this)
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

    fun setupObservers() {
        viewModel.viewState.observe(this) {
            it?.let { observeViewState(it) }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setBackgroundToActionBar() {
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                MaterialColors.getColor(
                    window.decorView,
                    R.attr.bg_color
                )
            )
        )
    }
}