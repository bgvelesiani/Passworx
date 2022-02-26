package com.gvelesiani.passmanager.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gvelesiani.passmanager.R
import com.gvelesiani.passmanager.base.BaseFragment
import com.gvelesiani.passmanager.databinding.FragmentBrowseBinding
import me.ibrahimsn.lib.SmoothBottomBar

class BrowseFragment: BaseFragment<BrowseViewModel, FragmentBrowseBinding>(BrowseViewModel::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBrowseBinding
        get() = FragmentBrowseBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        requireActivity().findViewById<SmoothBottomBar>(R.id.bottomBar).visibility = View.VISIBLE
        setUpOnClickListeners()
    }

    private fun setUpOnClickListeners() {
        binding.browseTrashedPasswords.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_browse_to_passwordTrashFragment)
        }
    }

    override fun setupObservers() {
    }
}