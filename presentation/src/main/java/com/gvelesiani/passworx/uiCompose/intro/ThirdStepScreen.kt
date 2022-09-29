package com.gvelesiani.passworx.uiCompose.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.gvelesiani.passworx.ui.intro.thirdStep.ThirdStepVM
import com.gvelesiani.passworx.uiCompose.components.Switch
import com.gvelesiani.passworx.uiCompose.composeTheme.secondaryTextColor
import com.gvelesiani.passworx.uiCompose.composeTheme.textColorDark
import com.gvelesiani.passworx.uiCompose.composeTheme.textColorLight
import org.koin.androidx.compose.getViewModel

@Composable
fun ThirdStepScreen(viewModel: ThirdStepVM = getViewModel()) {
    val context = LocalContext.current
    val textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_intro_third_step),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp),
            text = context.getString(R.string.intro_third_step_title),
            fontFamily = FontFamily(Font(R.font.medium)),
            color = textColor,
            fontSize = 26.sp
        )
        Text(
            modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp),
            text = context.getString(R.string.intro_third_step_subtitle),
            fontFamily = FontFamily(Font(R.font.medium)),
            color = secondaryTextColor,
            fontSize = 20.sp
        )

        Column(Modifier.padding(start = 16.dp, end = 16.dp)) {
            Switch(
                shouldBeChecked = false,
                text = context.getString(R.string.settings_take_screenshots),
                description = context.getString(R.string.take_in_app_screenshots_info)
            ) {
                viewModel.allowTakingScreenshots(it)
            }

            Switch(
                shouldBeChecked = false,
                text = context.getString(R.string.settings_unlock_with_biometrics),
                description = context.getString(R.string.unlock_with_biometrics_info)
            ) {
                viewModel.allowBiometrics(it)
            }
        }
    }
}