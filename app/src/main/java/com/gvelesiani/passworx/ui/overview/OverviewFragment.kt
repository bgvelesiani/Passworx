package com.gvelesiani.passworx.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.databinding.FragmentOverviewContainerBinding

class OverviewFragment :
    BaseFragment<OverviewVM, FragmentOverviewContainerBinding>(OverviewVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    FragmentOverviewContainerBinding = FragmentOverviewContainerBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            binding.ctBrowse.setOnClickListener {
                findNavController().navigate(R.id.action_overviewFragment_to_navigation_browse)
            }

            binding.ctGenerate.setOnClickListener {
                findNavController().navigate(R.id.action_overviewFragment_to_navigation_generate)
            }

            binding.ctSettings.setOnClickListener {
                findNavController().navigate(R.id.action_overviewFragment_to_navigation_settings)
            }

            binding.ctVault.setOnClickListener {
                findNavController().navigate(R.id.action_overviewFragment_to_navigation_passwords)
            }
        }
    }

    override fun setupObservers() {

    }
}
