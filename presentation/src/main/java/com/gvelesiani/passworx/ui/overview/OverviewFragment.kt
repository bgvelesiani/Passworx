package com.gvelesiani.passworx.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gvelesiani.base.BaseFragment
import com.gvelesiani.passworx.R
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
            ctBrowse.setOnClickListener {
                findNavController().navigate(R.id.action_overviewFragment_to_navigation_browse)
            }

            ctGenerate.setOnClickListener {
                findNavController().navigate(R.id.action_overviewFragment_to_navigation_generate)
            }

            ctSettings.setOnClickListener {
                findNavController().navigate(R.id.action_overviewFragment_to_navigation_settings)
            }

            ctVault.setOnClickListener {
                findNavController().navigate(R.id.action_overviewFragment_to_navigation_passwords)
            }

            ctBackupAndRestore.setOnClickListener {
                findNavController().navigate(R.id.action_overviewFragment_to_backupAndRestoreFragment)
            }
        }
    }

    override fun setupObservers() {

    }
}
