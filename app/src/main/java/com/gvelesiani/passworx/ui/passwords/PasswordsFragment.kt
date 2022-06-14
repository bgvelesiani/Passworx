package com.gvelesiani.passworx.ui.passwords

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.adapters.PasswordAdapter
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.databinding.FragmentPasswordsBinding
import com.gvelesiani.passworx.ui.passwordDetails.PasswordDetailsBottomSheet

class PasswordsFragment :
    BaseFragment<PasswordsVM, FragmentPasswordsBinding>(PasswordsVM::class) {
//    SearchView.OnQueryTextListener {
    private lateinit var adapter: PasswordAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPasswordsBinding
        get() = FragmentPasswordsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
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
            })
        binding.rvPasswords.adapter = adapter
        binding.rvPasswords.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupSearch(){
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.searchPasswords(newText.toString())
                } else {
                    viewModel.getPasswords()
                }
                return true
            }
        })
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        requireActivity().menuInflater.inflate(R.menu.options_menu, menu)
//        val searchView = (menu.findItem(R.id.search).actionView as SearchView)
//        searchView.setOnQueryTextListener(this)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

//    override fun onQueryTextSubmit(query: String?): Boolean {
//        return true
//    }
//
//    override fun onQueryTextChange(query: String?): Boolean {
//        if (query != null) {
//            viewModel.searchPasswords(query.toString())
//        } else {
//            viewModel.getPasswords()
//        }
//        return true
//    }
}