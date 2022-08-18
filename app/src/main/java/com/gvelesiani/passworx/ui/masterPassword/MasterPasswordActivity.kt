package com.gvelesiani.passworx.ui.masterPassword

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.color.MaterialColors
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseActivity
import com.gvelesiani.passworx.databinding.ActivityMasterPasswordBinding
import com.gvelesiani.passworx.ui.intro.IntroFragment
import com.gvelesiani.passworx.ui.masterPassword.fragments.MasterPasswordFragment
import com.gvelesiani.passworx.ui.masterPassword.fragments.createMasterPassword.CreateMasterPasswordFragment

class MasterPasswordActivity :
    BaseActivity<MasterPasswordAVM, ActivityMasterPasswordBinding>(MasterPasswordAVM::class) {
    override val bindingInflater: (LayoutInflater) -> ActivityMasterPasswordBinding =
        ActivityMasterPasswordBinding::inflate

    override fun setupView() {
        setupActionBar()
        setupObservers()
    }

    fun setupObservers() {
        viewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
        }

        viewModel.isIntroFinished.observe(this) {
            if (!it) {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<IntroFragment>(R.id.fContainer)
                }
            } else {
                viewModel.getMasterPassword()
            }
        }

        viewModel.masterPassword.observe(this) {
            if (it == "") {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<CreateMasterPasswordFragment>(R.id.fContainer)
                }
            } else {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<MasterPasswordFragment>(R.id.fContainer)
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