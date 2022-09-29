package com.gvelesiani.passworx.uiCompose

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.gvelesiani.passworx.common.extensions.hideKeyboard
import com.gvelesiani.passworx.navGraph.MainNavGraph
import com.gvelesiani.passworx.ui.MainVM
import com.gvelesiani.passworx.uiCompose.composeTheme.bgColorDark
import com.gvelesiani.passworx.uiCompose.composeTheme.bgColorLight
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainComposeActivity : ComponentActivity() {
    val viewModel: MainVM by viewModel()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideKeyboard()
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setContent {
            val isIntroFinished = viewModel.isIntroIsFinished.collectAsState()
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = if (isSystemInDarkTheme()) bgColorDark else bgColorLight
                ) {
                    MainNavGraph(true)
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