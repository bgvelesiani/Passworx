package com.gvelesiani.passworx.ui.passwords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.gvelesiani.base.BaseFragment
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.common.extensions.copyToClipboard
import com.gvelesiani.passworx.common.extensions.formatWebsite
import com.gvelesiani.passworx.common.extensions.hideKeyboard
import com.gvelesiani.passworx.databinding.FragmentPasswordsBinding
import com.gvelesiani.passworx.ui.composables.PasswordItem
import com.gvelesiani.passworx.ui.composables.SearchView
import com.gvelesiani.passworx.ui.composables.ToolbarView
import com.gvelesiani.passworx.ui.composeTheme.accentColor
import com.gvelesiani.passworx.ui.passwordDetails.PasswordDetailsBottomSheet


class PasswordsFragment :
    BaseFragment<PasswordsVM, FragmentPasswordsBinding>(PasswordsVM::class) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPasswordsBinding
        get() = FragmentPasswordsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        hideKeyboard()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        viewModel.getPasswords()
    }

    @Composable
    fun PasswordsContent(passwords: List<PasswordModel>) {
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize()) {
                ToolbarView("Passwords") {
                    findNavController().navigateUp()
                }
                SearchView {
                    viewModel.searchPasswords(it)
                }
                Spacer(modifier = Modifier.height(15.dp))
                LazyColumn(contentPadding = PaddingValues(top = 15.dp, bottom = 80.dp)) {
                    items(passwords) { password ->
                        val logoResource = LocalContext.current.resources.getIdentifier(
                            password.websiteOrAppName.formatWebsite(),
                            "drawable",
                            "com.gvelesiani.passworx"
                        )
                        PasswordItem(
                            logoResource,
                            password = password,
                            onCopyClick = {
                                viewModel.decryptPassword(password.password)
                            },
                            onFavoriteClick = {
                                viewModel.updateFavoriteState(
                                    !password.isFavorite,
                                    password.passwordId
                                )
                            },
                            onPasswordClick = {
                                PasswordDetailsBottomSheet.show(
                                    password,
                                    childFragmentManager,
                                    PasswordDetailsBottomSheet.TAG
                                )
                            }
                        )
                    }
                }
            }
            FloatingActionButton(
                backgroundColor = accentColor,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp)
                    .size(56.dp),
                onClick = {
                    findNavController().navigate(R.id.action_navigation_passwords_to_addNewPasswordFragment)
                }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "", tint = Color.White)
            }
        }
    }


    @Preview
    @Composable
    fun PasswordsContentPreview() {
        PasswordsContent(
            passwords = listOf(
                PasswordModel(
                    0,
                    "test",
                    "test",
                    "test",
                    "bgvelesiani2@gmail.com",
                    "test",
                    true,
                    true
                )
            )
        )
    }

    override fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            observeViewState(it)
        }
        viewModel.decryptedPassword.observe(viewLifecycleOwner) {
            it.copyToClipboard(requireContext())
            val snackbar = Snackbar.make(
                requireView(),
                getString(R.string.password_copying_success),
                Snackbar.LENGTH_SHORT
            )
            snackbar.show()
        }
    }

    private fun observeViewState(viewState: PasswordsVM.ViewState) {
        binding.content.setContent {
            MaterialTheme {
                PasswordsContent(passwords = viewState.passwords)
            }
        }
    }
}