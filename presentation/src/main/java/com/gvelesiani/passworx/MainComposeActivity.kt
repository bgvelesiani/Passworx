package com.gvelesiani.passworx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.gvelesiani.passworx.navGraph.MainNavGraph
import com.gvelesiani.passworx.ui.composeTheme.bgColorDark
import com.gvelesiani.passworx.ui.composeTheme.bgColorLight


class MainComposeActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = if (isSystemInDarkTheme()) bgColorDark else bgColorLight
                ) {
                    MainNavGraph()
                }
            }
        }
    }
}