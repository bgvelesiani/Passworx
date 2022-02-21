package com.gvelesiani.passmanager.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gvelesiani.passmanager.R
import com.gvelesiani.passmanager.base.BaseFragment
import com.gvelesiani.passmanager.databinding.FragmentToolsBinding

class ToolsFragment :
    BaseFragment<ToolsViewModel, FragmentToolsBinding>(ToolsViewModel::class) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentToolsBinding
        get() = FragmentToolsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        with(binding){
            toolGeneratePassword.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_tools_to_passwordGeneratorFragment)
            }
        }
    }
}