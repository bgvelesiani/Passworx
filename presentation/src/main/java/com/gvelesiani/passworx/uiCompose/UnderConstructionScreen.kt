package com.gvelesiani.passworx.uiCompose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.uiCompose.composeTheme.textColorDark
import com.gvelesiani.passworx.uiCompose.composeTheme.textColorLight

@Composable
fun UnderConstructionScreen(
    navController: NavController
) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(
            fontFamily = FontFamily(Font(R.font.medium)),
            fontSize = 16.sp,
            color = if(isSystemInDarkTheme()) textColorDark else textColorLight,
            text = "Screen is under construction \uD83E\uDD77\uD83C\uDFFB"
        )
    }
}