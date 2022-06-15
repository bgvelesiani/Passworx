package com.gvelesiani.passworx.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.databinding.FragmentSettingsBinding

class SettingsFragment :
    BaseFragment<SettingsVM, FragmentSettingsBinding>(SettingsVM::class) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingsBinding
        get() = FragmentSettingsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        viewModel.getMasterPassword()
    }

    override fun setupObservers() {
        viewModel.viewState.observe(this) { viewState ->
            binding.svChangeMasterPassword.setOnClickListener {
                if (viewState.masterPasswordExists == true) {
                    findNavController().navigate(R.id.changeMasterPasswordFragment)
                } else {
                    findNavController().navigate(R.id.createMasterPasswordFragment)
                }
            }
        }
    }
}