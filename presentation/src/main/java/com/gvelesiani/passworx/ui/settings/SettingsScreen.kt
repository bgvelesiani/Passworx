package com.gvelesiani.passworx.ui.settings

import android.app.Activity
import android.view.WindowManager
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.recreate
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.gvelesiani.common.models.PassworxColors
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.common.util.OnLifecycleEvent
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.ui.ThemeSharedVM
import com.gvelesiani.passworx.ui.components.Switch
import com.gvelesiani.passworx.ui.components.ToolbarView
import com.gvelesiani.passworx.ui.theme.supportsDynamic
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsVM = getViewModel(),
    sharedViewModel: ThemeSharedVM = getViewModel()
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val screenshots = viewModel.takingScreenshotsArePrevented.collectAsState()
    val biometrics = viewModel.biometricsAreAllowed.collectAsState()

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                sharedViewModel.getCurrentAppTheme(supportsDynamic())
            }

            else -> {}
        }
    }

    val currentThemeColors = remember {
        sharedViewModel.currentThemeColors
    }.collectAsState()

    val screenshotSwitchState by remember { mutableStateOf(screenshots) }
    val biometricsSwitchState by remember { mutableStateOf(biometrics) }

    Column(Modifier.fillMaxSize()) {
        ToolbarView(screenTitle = context.getString(R.string.title_settings)) {
            navController.navigateUp()
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .clickable {
                    navController.navigate(Screen.ChangeMasterPassword.route)
                },
            text = context.getString(R.string.settings_change_master_password_text),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
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

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 8.dp),
            text = "Change app colors",
            fontSize = 19.sp,
            fontFamily = FontFamily(Font(R.font.medium, FontWeight.Normal))
        )


        LazyRow(
            modifier = Modifier.animateContentSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            val themes = mutableListOf(
                Theme(
                    name = "Red",
                    isCurrent = currentThemeColors.value == PassworxColors.Red,
                    designId = R.drawable.red_theme
                ),
                Theme(
                    name = "Green",
                    isCurrent = currentThemeColors.value == PassworxColors.Green,
                    designId = R.drawable.green_theme
                ),
                Theme(
                    name = "Blue",
                    isCurrent = currentThemeColors.value == PassworxColors.Blue,
                    designId = R.drawable.blue_theme
                )
            )

            if (supportsDynamic())
                themes.add(
                    1,
                    Theme(
                        name = "Dynamic",
                        isCurrent = currentThemeColors.value == PassworxColors.Dynamic,
                        designId = R.drawable.dynamic_theme
                    )
                )

            items(themes) { theme ->
                ThemeComposable(
                    theme
                ) {
                    sharedViewModel.setThemeColors(
                        when (theme.name) {
                            "Red" -> PassworxColors.Red
                            "Blue" -> PassworxColors.Blue
                            "Green" -> PassworxColors.Green
                            else -> {
                                PassworxColors.Dynamic
                            }
                        }
                    )
                    recreate(context as Activity)
                }
            }
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

@Composable
fun ThemeComposable(theme: Theme, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier
                .padding(end = 6.dp)
                .height(230.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { onClick.invoke() }) {
            Image(painter = painterResource(id = theme.designId), contentDescription = "")

            if (theme.isCurrent) {
                FloatingActionButton(
                    containerColor = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 10.dp, end = 10.dp)
                        .size(23.dp),
                    onClick = {
                        onClick.invoke()
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }

        Text(
            text = theme.name,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.medium)),
            fontSize = 16.sp
        )
    }
}

data class Theme(
    val name: String = "Red",
    val isCurrent: Boolean = false,
    val designId: Int = 0
)