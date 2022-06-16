package com.gvelesiani.passworx.ui.masterPassword

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.material.color.MaterialColors
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseActivity
import com.gvelesiani.passworx.databinding.ActivityMasterPasswordBinding
import com.gvelesiani.passworx.ui.masterPassword.fragments.MasterPasswordFragment
import com.gvelesiani.passworx.ui.masterPassword.fragments.createMasterPassword.CreateMasterPasswordFragment

class MasterPasswordActivity :
    BaseActivity<MasterPasswordAVM, ActivityMasterPasswordBinding>(MasterPasswordAVM::class) {
    override val bindingInflater: (LayoutInflater) -> ActivityMasterPasswordBinding =
        ActivityMasterPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setupActionBar()
        viewModel.getMasterPassword()

        setupObservers()
    }

    fun setupObservers() {
        viewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
        }
        viewModel.masterPassword.observe(this) {
            if (it == "") {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<CreateMasterPasswordFragment>(R.id.fContainer)
                }
            } else {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<MasterPasswordFragment>(R.id.fContainer)
                }
            }
        }
    }

    private fun setupActionBar() {
        supportActionBar?.let { actionBar ->
            actionBar.setBackgroundDrawable(
                ColorDrawable(
                    MaterialColors.getColor(
                        binding.root,
                        R.attr.bg_color
                    )
                )
            )
            actionBar.elevation = 0F
            actionBar.title = ""
        }
    }
}