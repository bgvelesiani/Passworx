package com.gvelesiani.passworx.uiCompose

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.gvelesiani.passworx.common.extensions.hideKeyboard
import com.gvelesiani.passworx.navGraph.MainNavGraph
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.navGraph.StartScreen
import com.gvelesiani.passworx.uiCompose.components.LoadingScreen
import com.gvelesiani.passworx.uiCompose.composeTheme.bgColorDark
import com.gvelesiani.passworx.uiCompose.composeTheme.bgColorLight
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
            val startingScreen = remember {
                viewModel.startingScreenState
            }.collectAsState()

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = if (isSystemInDarkTheme()) bgColorDark else bgColorLight
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