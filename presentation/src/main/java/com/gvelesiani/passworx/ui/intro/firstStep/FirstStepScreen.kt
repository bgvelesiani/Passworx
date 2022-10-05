package com.gvelesiani.passworx.ui.intro.firstStep

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import com.gvelesiani.passworx.ui.composeTheme.secondaryTextColor

@Composable
fun FirstStepScreen() {
    val context = LocalContext.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.ic_intro_first_step), contentDescription = "")
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp),
            text = context.getString(R.string.intro_first_step_title),
            fontFamily = FontFamily(Font(R.font.medium)),
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