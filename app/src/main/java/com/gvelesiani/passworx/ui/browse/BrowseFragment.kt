package com.gvelesiani.passworx.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.databinding.FragmentBrowseBinding

class BrowseFragment :
    BaseFragment<BrowseVM, FragmentBrowseBinding>(BrowseVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBrowseBinding
        get() = FragmentBrowseBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar).visibility =
            View.VISIBLE
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