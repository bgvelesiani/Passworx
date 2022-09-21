package com.gvelesiani.passworx.ui.overview

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.ui.components.OverviewItem

@Composable
fun OverviewContent(navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.scrollable(
            state = scrollState,
            orientation = Orientation.Vertical
        )
    ) {
        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(R.id.action_overviewFragment_to_navigation_passwords)
            },
            image = R.drawable.ic_passwords,
            title = "Vault",
            subTitle = "Passwords of your favorite websites &amp; apps"
        )

        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(R.id.action_overviewFragment_to_navigation_browse)
            },
            image = R.drawable.ic_browse,
            title = "Browse",
            subTitle = "Browse favorites, trash"
        )

        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(R.id.action_overviewFragment_to_navigation_generate)
            },
            image = R.drawable.ic_generate,
            title = "Password Generator",
            subTitle = "Generate strong passwords"
        )

        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(R.id.action_overviewFragment_to_navigation_settings)
            },
            image = R.drawable.ic_settings,
            title = "Settings",
            subTitle = "Master Password, screenshots, biometriX"
        )

        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(R.id.action_overviewFragment_to_backupAndRestoreFragment)
            },
            image = R.drawable.ic_backup,
            title = "Backup and Restore",
            subTitle = "You can backup or restore passwords from generated files"
        )
    }
}