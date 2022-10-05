package com.gvelesiani.passworx.navGraph

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.ui.UnderConstructionScreen
import com.gvelesiani.passworx.ui.addPassword.AddPasswordScreen
import com.gvelesiani.passworx.ui.backupAndRestore.BackupAndRestoreScreen
import com.gvelesiani.passworx.ui.browse.BrowseScreen
import com.gvelesiani.passworx.ui.favorites.FavoritesScreen
import com.gvelesiani.passworx.ui.intro.IntroScreen
import com.gvelesiani.passworx.ui.masterPassword.MasterPasswordScreen
import com.gvelesiani.passworx.ui.masterPassword.changeMasterPassword.ChangeMasterPasswordScreen
import com.gvelesiani.passworx.ui.masterPassword.createMasterPassword.CreateMasterPasswordScreen
import com.gvelesiani.passworx.ui.overview.OverviewScreen
import com.gvelesiani.passworx.ui.passwordDetails.PasswordDetailsScreen
import com.gvelesiani.passworx.ui.passwordGenerator.PasswordGeneratorScreen
import com.gvelesiani.passworx.ui.passwords.PasswordsScreen
import com.gvelesiani.passworx.ui.settings.SettingsScreen
import com.gvelesiani.passworx.ui.trash.TrashScreen
import com.gvelesiani.passworx.ui.updatePassword.UpdatePasswordScreen

sealed class StartScreen {
    object Create : StartScreen()
    object Intro : StartScreen()
    object Master : StartScreen()
    object None : StartScreen()
}

@Composable
@ExperimentalAnimationApi
fun MainNavGraph(startScreen: String) {

    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController,
        startDestination = startScreen,
        enterTransition = { fadeIn(animationSpec = tween(800)) },
        exitTransition = { fadeOut(animationSpec = tween(800)) }
    ) {
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
        composable(route = Screen.Intro.route) {
            IntroScreen(navController)
        }
        composable(route = Screen.CreateMasterPassword.route) {
            CreateMasterPasswordScreen(navController)
        }
        composable(route = Screen.ChangeMasterPassword.route) {
            ChangeMasterPasswordScreen(navController)
        }
        composable(route = Screen.MasterPassword.route) {
            MasterPasswordScreen(navController)
        }
        composable(route = Screen.Trash.route) {
            TrashScreen(navController)
        }
        composable(route = Screen.UpdatePassword.route) {
            val passwordModel =
                navController.previousBackStackEntry?.savedStateHandle?.get<PasswordModel>("password")
            UpdatePasswordScreen(passwordModel ?: PasswordModel(), navController)
        }
        composable(route = Screen.Details.route,
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(800),
                    towards = AnimatedContentScope.SlideDirection.Up
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(800),
                    towards = AnimatedContentScope.SlideDirection.Down
                )
            }) {
            val passwordModel =
                navController.previousBackStackEntry?.savedStateHandle?.get<PasswordModel>("password")
            PasswordDetailsScreen(navController, password = passwordModel ?: PasswordModel())
        }
    }
}

sealed class Screen(val route: String) {
    object UpdatePassword : Screen("updatePassword")
    object Passwords : Screen("passwords")
    object AddNewPassword : Screen("addNewPassword")
    object Overview : Screen("overview")
    object Browse : Screen("browse")
    object UnderConstruction : Screen("underConstruction")
    object BackupAndRestore : Screen("backupAndRestore")
    object Settings : Screen("settings")
    object Favorites : Screen("favorites")
    object Generate : Screen("generatePassword")
    object Intro : Screen("introScreen")
    object CreateMasterPassword : Screen("createMasterPassword")
    object ChangeMasterPassword : Screen("changeMasterPassword")
    object MasterPassword : Screen("masterPassword")
    object Trash : Screen("trash")
    object Details : Screen("passwordDetails")
}