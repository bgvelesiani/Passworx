package com.gvelesiani.passworx.uiCompose.intro.firstStep

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.uiCompose.composeTheme.secondaryTextColor
import com.gvelesiani.passworx.uiCompose.composeTheme.textColorDark
import com.gvelesiani.passworx.uiCompose.composeTheme.textColorLight

@Composable
fun FirstStepScreen() {
    val context = LocalContext.current
    val textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.ic_intro_first_step), contentDescription = "")
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp),
            text = context.getString(R.string.intro_first_step_title),
            fontFamily = FontFamily(Font(R.font.medium)),
            color = textColor,
            fontSize = 26.sp
        )
        Text(
            modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp),
            text = context.getString(R.string.intro_first_step_subtitle),
            fontFamily = FontFamily(Font(R.font.medium)),
            color = secondaryTextColor,
            fontSize = 20.sp
        )
    }
}