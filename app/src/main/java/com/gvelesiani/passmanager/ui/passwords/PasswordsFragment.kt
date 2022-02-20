package com.gvelesiani.passmanager.ui.passwords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gvelesiani.passmanager.base.BaseFragment
import com.gvelesiani.passmanager.databinding.FragmentPasswordsBinding

class PasswordsFragment : BaseFragment<PasswordsViewModel, FragmentPasswordsBinding>(
    PasswordsViewModel::class
) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPasswordsBinding
        get() = FragmentPasswordsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        viewModel.text.observe(viewLifecycleOwner) {
            binding.textDashboard.text = it
        }
    }
}