package com.gvelesiani.passworx.ui.browse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.ui.components.OverviewItem
import com.gvelesiani.passworx.ui.components.ToolbarView

@Composable
fun BrowseScreen(
    navController: NavController
) {
    Column(Modifier.fillMaxSize()) {
        ToolbarView(LocalContext.current.getString(R.string.title_browse)) {
            navController.navigateUp()
        }
        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(R.id.action_navigation_browse_to_passwordFavouritesFragment)
            },
            image = R.drawable.ic_not_favorite,
            title = "Favorites",
            subTitle = "Manage your favorite passwords"
        )
        OverviewItem(
            onOverviewItemClick = {
                navController.navigate(R.id.action_navigation_browse_to_passwordTrashFragment)
            },
            image = R.drawable.ic_passwords,
            title = "Trash",
            subTitle = "Restore or Delete your trashed passwords permanently"
        )
    }
}