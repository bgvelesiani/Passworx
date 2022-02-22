package com.gvelesiani.passmanager.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gvelesiani.passmanager.R
import com.gvelesiani.passmanager.base.BaseFragment
import com.gvelesiani.passmanager.databinding.FragmentToolsBinding
import me.ibrahimsn.lib.SmoothBottomBar

class ToolsFragment :
    BaseFragment<ToolsViewModel, FragmentToolsBinding>(ToolsViewModel::class) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentToolsBinding
        get() = FragmentToolsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        requireActivity().findViewById<SmoothBottomBar>(R.id.bottomBar).visibility = View.VISIBLE
        with(binding) {
            toolGeneratePassword.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_tools_to_passwordGeneratorFragment)
            }
        }
    }

    override fun setupObservers() {
    }
}