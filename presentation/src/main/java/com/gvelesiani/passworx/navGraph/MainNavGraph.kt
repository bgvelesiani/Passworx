package com.gvelesiani.passworx.navGraph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.gvelesiani.passworx.uiCompose.UnderConstructionScreen
import com.gvelesiani.passworx.uiCompose.addPassword.AddPasswordScreen
import com.gvelesiani.passworx.uiCompose.backupAndRestore.BackupAndRestoreScreen
import com.gvelesiani.passworx.uiCompose.browse.BrowseScreen
import com.gvelesiani.passworx.uiCompose.favorites.FavoritesScreen
import com.gvelesiani.passworx.uiCompose.overview.OverviewScreen
import com.gvelesiani.passworx.uiCompose.passwordGenerator.PasswordGeneratorScreen
import com.gvelesiani.passworx.uiCompose.passwords.PasswordsScreen
import com.gvelesiani.passworx.uiCompose.settings.SettingsScreen

@Composable
@ExperimentalAnimationApi
fun MainNavGraph() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController, startDestination = Screen.Overview.route) {
        composable(route = Screen.Overview.route) {
            OverviewScreen(navController)
        }
        composable(route = Screen.AddNewPassword.route) {
            AddPasswordScreen(navController)
        }
        composable(route = Screen.UnderConstruction.route) {
            UnderConstructionScreen(navController)
        }
        composable(route = Screen.Browse.route) {
            BrowseScreen(navController)
        }
        composable(route = Screen.Passwords.route) {
            PasswordsScreen(navController)
        }
        composable(route = Screen.BackupAndRestore.route) {
            BackupAndRestoreScreen(navController)
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(navController)
        }
        composable(route = Screen.Favorites.route) {
            FavoritesScreen(navController)
        }
        composable(route = Screen.Generate.route) {
            PasswordGeneratorScreen(navController)
        }
    }
}

sealed class Screen(val route: String) {
    object Passwords : Screen("passwords")
    object AddNewPassword : Screen("addNewPassword")
    object Overview : Screen("overview")
    object Browse : Screen("browse")
    object UnderConstruction : Screen("underConstruction")
    object BackupAndRestore : Screen("backupAndRestore")
    object Settings : Screen("settings")
    object Favorites : Screen("favorites")
    object Generate : Screen("generatePassword")
}