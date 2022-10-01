package com.gvelesiani.passworx.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.gvelesiani.passworx.common.extensions.hideKeyboard
import com.gvelesiani.passworx.navGraph.MainNavGraph
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.navGraph.StartScreen
import com.gvelesiani.passworx.ui.theme.PassworxTheme
import com.gvelesiani.passworx.ui.theme.supportsDynamic
import com.gvelesiani.passworx.ui.components.LoadingScreen
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainComposeActivity : FragmentActivity() {
    val viewModel: MainVM by viewModel()

    @SuppressLint("SourceLockedOrientationActivity")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        hideKeyboard()
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setContent {
            val context = LocalContext.current
            if(supportsDynamic()){
                window?.statusBarColor = if(isSystemInDarkTheme()) dynamicDarkColorScheme(context).surface.toArgb() else dynamicLightColorScheme(context).surface.toArgb()
            } else {
                window?.statusBarColor = MaterialTheme.colorScheme.surface.toArgb()
            }

            val startingScreen = remember {
                viewModel.startingScreenState
            }.collectAsState()

            PassworxTheme {
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