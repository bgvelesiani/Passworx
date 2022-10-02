package com.gvelesiani.passworx.ui.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.ui.components.OverviewItem
import com.gvelesiani.passworx.ui.components.ToolbarView

@Composable
fun OverviewScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        ToolbarView(isHomeScreen = true, screenTitle = LocalContext.current.getString(R.string.app_name)) {
        }
        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(Screen.Passwords.route)
            },
            image = R.drawable.ic_passwords,
            title = "Vault",
            subTitle = "Passwords of your favorite websites &amp; apps"
        )

        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(Screen.Browse.route)
            },
            image = R.drawable.ic_browse,
            title = "Browse",
            subTitle = "Browse favorites, trash"
        )

        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(Screen.Generate.route)
            },
            image = R.drawable.ic_generate,
            title = "Password Generator",
            subTitle = "Generate strong passwords"
        )

        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(Screen.Settings.route)
            },
            image = R.drawable.ic_settings,
            title = "Settings",
            subTitle = "Master Password, screenshots, biometriX"
        )

        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(Screen.BackupAndRestore.route)
            },
            image = R.drawable.ic_backup,
            title = "Backup and Restore",
            subTitle = "You can backup or restore passwords from generated files"
        )

    }
}