package com.gvelesiani.passworx.ui.trash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.databinding.FragmentPasswordsBinding
import com.gvelesiani.passworx.ui.passwordDetails.PasswordDetailsBottomSheet
import com.gvelesiani.passworx.adapters.PasswordAdapter

class PasswordTrashFragment :
    BaseFragment<PasswordTrashVM, FragmentPasswordsBinding>(PasswordTrashVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPasswordsBinding =
        FragmentPasswordsBinding::inflate

    private lateinit var adapter: PasswordAdapter

    override fun setupView(savedInstanceState: Bundle?) {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomBar).visibility = View.GONE
        binding.btAddPassword.visibility = View.GONE
        viewModel.getPasswords()
        setupRecyclerViewAdapter()
    }

    override fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            observeViewState(it)
        })
    }

    private fun observeViewState(viewState: PasswordTrashVM.ViewState) {
        if (viewState.passwords.isEmpty()) {
            binding.rvPasswords.isVisible = false
            binding.groupNoData.isVisible = true
            binding.tvNoData.text = getString(R.string.empty_trash_title)
            binding.tvNoDataDesc.text = getString(R.string.empty_trash_message)
        } else {
            adapter.submitData(viewState.passwords)
            binding.groupNoData.isVisible = false
            binding.rvPasswords.isVisible = true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showMenu(v: View, @MenuRes menuRes: Int, password: PasswordModel, position: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menuEditAndRestore -> Toast.makeText(
                    requireContext(),
                    "Edit",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.menuDeletePermanently -> {
                    MaterialAlertDialogBuilder(
                        requireContext(),
                        R.style.MaterialAlertDialog_Material3_Body_Text
                    )
                        .setMessage("Are you sure you want to delete this password permanently?")
                        .setNegativeButton("No") { _, _ ->
                            // Respond to negative button press
                        }
                        .setPositiveButton("Yes") { _, _ ->
                            viewModel.deletePassword(passwordId = password.passwordId)
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
            menuClickListener = { password: PasswordModel, view: View, position: Int ->// it == PasswordModel
                showMenu(
                    view,
                    R.menu.trashed_passwords_menu,
                    password,
                    position
                )
            })
        binding.rvPasswords.adapter = adapter
        binding.rvPasswords.layoutManager = LinearLayoutManager(requireContext())
    }
}