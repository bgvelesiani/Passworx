package com.gvelesiani.passworx.ui.passwords

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.adapters.PasswordAdapter
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.common.copyToClipboard
import com.gvelesiani.passworx.common.onTextChanged
import com.gvelesiani.passworx.databinding.FragmentPasswordsBinding
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.ui.passwordDetails.PasswordDetailsBottomSheet


class PasswordsFragment :
    BaseFragment<PasswordsVM, FragmentPasswordsBinding>(PasswordsVM::class) {
    private lateinit var adapter: PasswordAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPasswordsBinding
        get() = FragmentPasswordsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        binding.btAddPassword.visibility = View.VISIBLE
        viewModel.getPasswords()
        setupSearch()
        setupRecyclerViewAdapter()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btAddPassword.setOnClickListener {
            findNavController().navigate(R.id.addNewPasswordFragment)
        }
    }

    override fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            observeViewState(it)
        }
    }

    private fun observeViewState(viewState: PasswordsVM.ViewState) {
        binding.progressBar.isVisible = viewState.isLoading
        when (viewState.isLoading) {
            true -> {
                binding.rvPasswords.isVisible = false
                binding.groupNoData.isVisible = false
            }
            else -> {
                if (viewState.passwords.isEmpty()) {
                    binding.groupNoData.isVisible = true
                    binding.rvPasswords.isVisible = false
                } else {
                    adapter.submitData(viewState.passwords)
                    binding.groupNoData.isVisible = false
                    binding.rvPasswords.isVisible = true
                }
            }
        }
        viewState.decryptedPassword?.let {
            it.copyToClipboard(requireContext())
            val snackbar = Snackbar.make(
                requireView(),
                getString(R.string.password_copying_success),
                Snackbar.LENGTH_SHORT
            )
            snackbar.anchorView = binding.btAddPassword
            snackbar.show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showMenu(v: View, @MenuRes menuRes: Int, password: PasswordModel, position: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menuEdit -> Toast.makeText(requireContext(), "Edit", Toast.LENGTH_SHORT).show()
                R.id.menuDelete -> {
                    MaterialAlertDialogBuilder(
                        requireContext()
                    )
                        .setMessage(getString(R.string.move_to_trash_dialog_message))
                        .setNegativeButton(getString(R.string.dialog_no)) { _, _ ->
                        }
                        .setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
                            viewModel.updateItemTrashState(!password.isInTrash, password.passwordId)
                            viewModel.getPasswords()
                            adapter.notifyItemChanged(position)
                            adapter.notifyDataSetChanged()
                        }
                        .show()
                }
            }
            true
        }
        popup.setOnDismissListener {
        }
        popup.show()
    }

    private fun setupRecyclerViewAdapter() {
        adapter = PasswordAdapter(
            clickListener = { password: PasswordModel ->
                PasswordDetailsBottomSheet.show(
                    password,
                    childFragmentManager,
                    PasswordDetailsBottomSheet.TAG
                )
            },
            menuClickListener = { password: PasswordModel, view: View, position: Int ->
                showMenu(
                    view,
                    R.menu.password_item_menu,
                    password,
                    position
                )
            },
            copyClickListener = { passwordModel ->
                viewModel.decryptPassword(passwordModel.password)
            })
        binding.rvPasswords.adapter = adapter
        binding.rvPasswords.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupSearch() {
        binding.btClearSearch.setOnClickListener {
            binding.search.text?.clear()
        }
        binding.search.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.btSearch.visibility = View.GONE
                binding.btClearSearch.animation =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_animation)
                binding.btClearSearch.visibility = View.VISIBLE
            } else {
                binding.btSearch.visibility = View.VISIBLE
                binding.btClearSearch.visibility = View.GONE
            }
        }
        binding.search.onTextChanged {
            viewModel.searchPasswords(it)
        }
    }
}