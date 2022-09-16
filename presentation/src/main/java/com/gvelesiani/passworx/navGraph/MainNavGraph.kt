package com.gvelesiani.passworx.navGraph

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.gvelesiani.passworx.ui.UnderConstructionScreen
import com.gvelesiani.passworx.ui.addPassword.AddPasswordScreen
import com.gvelesiani.passworx.ui.browse.BrowseScreen
import com.gvelesiani.passworx.ui.masterPassword.MasterPasswordScreen
import com.gvelesiani.passworx.ui.overview.OverviewScreen
import com.gvelesiani.passworx.ui.passwords.PasswordsScreen

@Composable
@ExperimentalAnimationApi
fun MainNavGraph() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController, startDestination = Screen.MasterPassword.route) {
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
        composable(route = Screen.Passwords.route // ,
//            enterTransition = {},
//            exitTransition = {}
        ) {
            PasswordsScreen(navController)
        }
        composable(route = Screen.MasterPassword.route) {
            MasterPasswordScreen(navController)
        }
    }
}

sealed class Screen(val route: String) {
    object Passwords : Screen("passwords")
    object AddNewPassword : Screen("addNewPassword")
    object Overview : Screen("overview")
    object Browse : Screen("browse")
    object UnderConstruction: Screen("underConstruction")
    object MasterPassword: Screen("masterPassword")
}