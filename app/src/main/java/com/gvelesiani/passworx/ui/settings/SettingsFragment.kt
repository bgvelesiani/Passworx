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
        binding.svChangeMasterPassword.setOnClickListener {
            findNavController().navigate(R.id.changeMasterPasswordFragment)
        }
    }

    override fun setupObservers() {
    }
}