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

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.backClickArea.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.svChangeMasterPassword.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_settings_to_changeMasterPasswordFragment)
        }
    }

    override fun setupObservers() {
    }
}