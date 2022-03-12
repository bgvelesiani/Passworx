package com.gvelesiani.passworx.ui.masterPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.databinding.FragmentMasterPasswordBinding

class MasterPasswordFragment: BaseFragment<MasterPasswordVM, FragmentMasterPasswordBinding>(MasterPasswordVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMasterPasswordBinding = FragmentMasterPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar).visibility = View.GONE
        binding.btGoToVault.setOnClickListener {
            findNavController().navigate(R.id.action_masterPasswordFragment_to_navigation_passwords)
        }
    }

    override fun setupObservers() {

    }
}