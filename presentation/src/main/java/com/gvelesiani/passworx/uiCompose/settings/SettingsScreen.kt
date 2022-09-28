package com.gvelesiani.passworx.uiCompose.settings

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.uiCompose.components.Switch
import com.gvelesiani.passworx.uiCompose.components.ToolbarView
import com.gvelesiani.passworx.uiCompose.composeTheme.bgSecondaryDark
import com.gvelesiani.passworx.uiCompose.composeTheme.bgSecondaryLight
import com.gvelesiani.passworx.uiCompose.composeTheme.textColorDark
import com.gvelesiani.passworx.uiCompose.composeTheme.textColorLight
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsVM = getViewModel()
) {
    // TODO: Change master password navigation
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val screenshots = viewModel.takingScreenshotsArePrevented.collectAsState()
    val biometrics = viewModel.biometricsAreAllowed.collectAsState()

    val screenshotSwitchState by remember { mutableStateOf(screenshots) }
    val biometricsSwitchState by remember { mutableStateOf(biometrics) }

    Column(Modifier.fillMaxSize()) {
        ToolbarView(screenTitle = context.getString(R.string.title_settings)) {
            navController.navigateUp()
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            color = if (isSystemInDarkTheme()) textColorDark else textColorLight,
            text = context.getString(R.string.settings_change_master_password_text),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth().background(if(isSystemInDarkTheme()) bgSecondaryDark else bgSecondaryLight)) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                color = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                text = context.getString(R.string.settings_preferences_header),
                fontSize = 19.sp,
                fontFamily = FontFamily(Font(R.font.medium, FontWeight.Normal))
            )
        }

        Switch(
            shouldBeChecked = screenshotSwitchState.value,
            text = context.getString(R.string.settings_take_screenshots),
            description = context.getString(R.string.take_in_app_screenshots_info)
        ) {
            viewModel.allowTakingScreenshots(it)
        }

        Switch(
            shouldBeChecked = biometricsSwitchState.value,
            text = context.getString(R.string.settings_unlock_with_biometrics),
            description = context.getString(R.string.unlock_with_biometrics_info)
        ) {
            viewModel.allowBiometrics(it)
        }

        if (screenshots.value) {
            /**
             * With FLAG_SECURE, Users will be prevented from taking screenshots of the application,
             * */
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        } else {
            activity.window.clearFlags(
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }
}