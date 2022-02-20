package com.gvelesiani.passmanager.ui.checkPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gvelesiani.passmanager.base.BaseFragment
import com.gvelesiani.passmanager.databinding.FragmentCheckPasswordBinding

class CheckPasswordFragment :
    BaseFragment<CheckPasswordViewModel, FragmentCheckPasswordBinding>(CheckPasswordViewModel::class) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCheckPasswordBinding
        get() = FragmentCheckPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        viewModel.text.observe(viewLifecycleOwner) {
            binding.textNotifications.text = it
        }
    }
}