package com.gvelesiani.passworx.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.fragment.findNavController
import com.gvelesiani.base.BaseFragment
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.databinding.FragmentOverviewContainerBinding
import com.gvelesiani.passworx.ui.composables.OverviewItem

class OverviewFragment :
    BaseFragment<OverviewVM, FragmentOverviewContainerBinding>(OverviewVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    FragmentOverviewContainerBinding = FragmentOverviewContainerBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        binding.content.setContent {
            OverviewContent()
        }
    }

    @Composable
    fun OverviewContent() {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.scrollable(
                state = scrollState,
                orientation = Orientation.Vertical
            )
        ) {
            OverviewItem(
                onOverviewItemClick = {
                    findNavController().navigate(R.id.action_overviewFragment_to_navigation_passwords)
                },
                image = R.drawable.ic_passwords,
                title = "Vault",
                subTitle = "Passwords of your favorite websites &amp; apps"
            )

            OverviewItem(
                onOverviewItemClick = {
                    findNavController().navigate(R.id.action_overviewFragment_to_navigation_browse)
                },
                image = R.drawable.ic_browse,
                title = "Browse",
                subTitle = "Browse favorites, trash"
            )

            OverviewItem(
                onOverviewItemClick = {
                    findNavController().navigate(R.id.action_overviewFragment_to_navigation_generate)
                },
                image = R.drawable.ic_generate,
                title = "Password Generator",
                subTitle = "Generate strong passwords"
            )

            OverviewItem(
                onOverviewItemClick = {
                    findNavController().navigate(R.id.action_overviewFragment_to_navigation_settings)
                },
                image = R.drawable.ic_settings,
                title = "Settings",
                subTitle = "Master Password, screenshots, biometriX"
            )

            OverviewItem(
                onOverviewItemClick = {
                    findNavController().navigate(R.id.action_overviewFragment_to_backupAndRestoreFragment)
                },
                image = R.drawable.ic_backup,
                title = "Backup and Restore",
                subTitle = "You can backup or restore passwords from generated files"
            )
        }
    }

    override fun setupObservers() {

    }
}
