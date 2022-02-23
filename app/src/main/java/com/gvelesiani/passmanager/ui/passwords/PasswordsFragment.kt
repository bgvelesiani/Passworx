package com.gvelesiani.passmanager.ui.passwords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gvelesiani.passmanager.R
import com.gvelesiani.passmanager.base.BaseFragment
import com.gvelesiani.passmanager.data.models.PasswordModel
import com.gvelesiani.passmanager.databinding.FragmentPasswordsBinding
import com.gvelesiani.passmanager.ui.addPassword.AddPasswordViewModel
import com.gvelesiani.passmanager.ui.passwords.adapter.PasswordAdapter
import me.ibrahimsn.lib.SmoothBottomBar

class PasswordsFragment : BaseFragment<PasswordsViewModel, FragmentPasswordsBinding>(
    PasswordsViewModel::class
) {
    private lateinit var adapter: PasswordAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPasswordsBinding
        get() = FragmentPasswordsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        viewModel.getPasswords()
        setupRecyclerViewAdapter()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btAddPassword.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_passwords_to_addPasswordFragment)
        }
    }

    override fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            observeViewState(it)
        })
    }

    private fun observeViewState(viewState: PasswordsViewModel.ViewState) {
        adapter.submitData(viewState.passwords)
    }

    private fun setupRecyclerViewAdapter(){
        adapter = PasswordAdapter { password: PasswordModel ->
            Toast.makeText(requireContext(), password.emailOrUserName, Toast.LENGTH_SHORT).show()
        }
        binding.rvPasswords.adapter = adapter
        binding.rvPasswords.layoutManager = LinearLayoutManager(requireContext())
    }
}