package com.gvelesiani.passworx.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gvelesiani.base.BaseFragment
import com.gvelesiani.passworx.databinding.FragmentOverviewContainerBinding

class OverviewFragment :
    BaseFragment<OverviewVM, FragmentOverviewContainerBinding>(OverviewVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    FragmentOverviewContainerBinding = FragmentOverviewContainerBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        binding.content.setContent {
            OverviewScreen(findNavController())
        }
    }

    override fun setupObservers() {

    }
}
