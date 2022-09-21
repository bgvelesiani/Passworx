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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.gvelesiani.base.BaseFragment
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.common.extensions.copyToClipboard
import com.gvelesiani.passworx.common.extensions.formatWebsite
import com.gvelesiani.passworx.common.extensions.hideKeyboard
import com.gvelesiani.passworx.databinding.FragmentPasswordsBinding
import com.gvelesiani.passworx.ui.components.PasswordItem
import com.gvelesiani.passworx.ui.components.SearchView
import com.gvelesiani.passworx.ui.components.ToolbarView
import com.gvelesiani.passworx.ui.composeTheme.accentColor
import com.gvelesiani.passworx.ui.passwordDetails.PasswordDetailsBottomSheet


class PasswordsFragment :
    BaseFragment<PasswordsVM, FragmentPasswordsBinding>(PasswordsVM::class) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPasswordsBinding
        get() = FragmentPasswordsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        hideKeyboard()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun setupObservers() {
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
}