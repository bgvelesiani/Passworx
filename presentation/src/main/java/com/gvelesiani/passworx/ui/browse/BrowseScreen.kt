package com.gvelesiani.passworx.ui.browse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.ui.components.OverviewItem
import com.gvelesiani.passworx.ui.components.ToolbarView

@Composable
fun BrowseScreen(
    navController: NavController
) {
    Column(Modifier.fillMaxSize()) {
        ToolbarView(screenTitle = LocalContext.current.getString(R.string.title_browse)) {
            navController.navigateUp()
        }
        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(Screen.UnderConstruction.route)
            },
            image = R.drawable.ic_not_favorite,
            title = "Favorites",
            subTitle = "Manage your favorite passwords"
        )
        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(Screen.UnderConstruction.route)
            },
            image = R.drawable.ic_passwords,
            title = "Trash",
            subTitle = "Restore or Delete your trashed passwords permanently"
        )
    }
}