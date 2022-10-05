package com.gvelesiani.passworx.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.gvelesiani.common.models.PassworxColors
import com.gvelesiani.passworx.common.extensions.hideKeyboard
import com.gvelesiani.passworx.navGraph.MainNavGraph
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.navGraph.StartScreen
import com.gvelesiani.passworx.ui.components.LoadingScreen
import com.gvelesiani.passworx.ui.theme.PassworxTheme
import com.gvelesiani.passworx.ui.theme.RedThemeDarkColors
import com.gvelesiani.passworx.ui.theme.RedThemeLightColors
import com.gvelesiani.passworx.ui.theme.blue.BlueThemeDarkColors
import com.gvelesiani.passworx.ui.theme.blue.BlueThemeLightColors
import com.gvelesiani.passworx.ui.theme.green.GreenThemeDarkColors
import com.gvelesiani.passworx.ui.theme.green.GreenThemeLightColors
import com.gvelesiani.passworx.ui.theme.supportsDynamic
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainComposeActivity : FragmentActivity() {
    private val viewModel: MainVM by viewModel()
    private val sharedViewModel: ThemeSharedVM by viewModel()

    @SuppressLint("SourceLockedOrientationActivity")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        hideKeyboard()
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setContent {
            sharedViewModel.getCurrentAppTheme(supportsDynamic())
            val context = LocalContext.current

            val startingScreen = remember {
                viewModel.startingScreenState
            }.collectAsState()

            val currentThemeColors = remember {
                sharedViewModel.currentThemeColors
            }.collectAsState()

            when (currentThemeColors.value) {
                PassworxColors.Dynamic -> {
                    window?.statusBarColor =
                        if (isSystemInDarkTheme()) dynamicDarkColorScheme(context).surface.toArgb() else dynamicLightColorScheme(
                            context
                        ).surface.toArgb()
                }

                PassworxColors.Red -> {
                    window?.statusBarColor = if(isSystemInDarkTheme()) RedThemeDarkColors.surface.toArgb() else RedThemeLightColors.surface.toArgb()
                }
                PassworxColors.Green -> {
                    window?.statusBarColor = if(isSystemInDarkTheme()) GreenThemeDarkColors.surface.toArgb() else GreenThemeLightColors.surface.toArgb()
                }
                PassworxColors.Blue -> {
                    window?.statusBarColor = if(isSystemInDarkTheme()) BlueThemeDarkColors.surface.toArgb() else BlueThemeLightColors.surface.toArgb()
                }
            }

            PassworxTheme(currentThemeColors.value) {
                androidx.compose.material3.Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (startingScreen.value) {
                        is StartScreen.Intro -> {
                            MainNavGraph(
                                Screen.Intro.route
                            )
                        }

                        is StartScreen.Create -> {
                            MainNavGraph(
                                Screen.CreateMasterPassword.route
                            )
                        }

                        is StartScreen.Master -> {
                            MainNavGraph(
                                Screen.MasterPassword.route
                            )
                        }

                        StartScreen.None -> {
                            LoadingScreen()
                        }
                    }
                }
            }
            SetupObservers()
        }
    }

    @Composable
    private fun SetupObservers() {
        val prevented = viewModel.takingScreenshotsArePrevented.collectAsState()
        if (prevented.value) {
            /**
             * With FLAG_SECURE, Users will be prevented from taking screenshots of the application,
             * */
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        } else {
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }
}