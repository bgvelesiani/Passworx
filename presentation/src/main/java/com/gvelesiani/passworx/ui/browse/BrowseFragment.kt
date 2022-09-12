package com.gvelesiani.passworx.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gvelesiani.base.BaseFragment
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.databinding.FragmentBrowseBinding

class BrowseFragment :
    BaseFragment<BrowseVM, FragmentBrowseBinding>(BrowseVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBrowseBinding
        get() = FragmentBrowseBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        binding.toolbar.setupToolbar {
            findNavController().navigateUp()
        }
        setUpOnClickListeners()
    }

    private fun setUpOnClickListeners() {
        binding.browseTrashedPasswords.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_browse_to_passwordTrashFragment)
        }
        binding.browseFavoritePasswords.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_browse_to_passwordFavouritesFragment)
        }
    }

    override fun setupObservers() {
    }
}