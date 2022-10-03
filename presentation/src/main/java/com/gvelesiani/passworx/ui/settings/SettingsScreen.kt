package com.gvelesiani.passworx.ui.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.setValue
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
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.ui.MainComposeActivity
import com.gvelesiani.passworx.ui.ThemeSharedVM
import com.gvelesiani.passworx.ui.components.Switch
import com.gvelesiani.passworx.ui.components.ToolbarView
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

//        FlowRow(
//            mainAxisSpacing = 16.dp,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Box(
//                    modifier = Modifier
//                        .size(50.dp)
//                        .clip(CircleShape)
//                        .background(RedThemeLightColors.primary)
//                        .clickable {
//                            sharedViewModel.setThemeColors(PassworxColors.Red)
//                            restart(context)
//                        }
//                )
//                Text(
//                    textAlign = TextAlign.Center,
//                    text = "Red",
//                    fontSize = 14.sp,
//                    fontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
//                )
//            }
//
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Box(
//                    modifier = Modifier
//                        .size(50.dp)
//                        .clip(CircleShape)
//                        .background(OrangeThemeLightColors.primary)
//                        .clickable {
//                            sharedViewModel.setThemeColors(PassworxColors.Orange)
//                            restart(context)
//                        }
//                )
//
//                Text(
//                    textAlign = TextAlign.Center,
//                    text = "Orange",
//                    fontSize = 14.sp,
//                    fontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
//                )
//
//            }
//
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Box(
//                    modifier = Modifier
//                        .size(50.dp)
//                        .clip(CircleShape)
//                        .background(BlueThemeLightColors.primary)
//                        .clickable {
//                            sharedViewModel.setThemeColors(PassworxColors.Blue)
//                            restart(context)
//                        }
//                )
//                Text(
//                    textAlign = TextAlign.Center,
//                    text = "Blue",
//                    fontSize = 14.sp,
//                    fontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
//                )
//            }
//
//
//            if (supportsDynamic())
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Box(
//                        modifier = Modifier
//                            .size(50.dp)
//                            .clip(CircleShape)
//                            .background(
//                                Brush.horizontalGradient(
//                                    colors = listOf(
//                                        Color(0xffc5f9d7),
//                                        Color(0xFFF7D486),
//                                        Color(0xfff27a7d)
//                                    )
//                                )
//                            )
//                            .clickable {
//                                sharedViewModel.setThemeColors(PassworxColors.Dynamic)
//                                restart(context)
//                            }
//                    )
//                    Text(
//                        textAlign = TextAlign.Center,
//                        text = "Dynamic",
//                        fontSize = 14.sp,
//                        fontFamily = FontFamily(Font(R.font.medium, FontWeight.Normal))
//                    )
//                }
//        }

        LazyRow(
            modifier = Modifier.animateContentSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(4) { index ->
                ThemeComposable(
                    index
                ){

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

fun restart(context: Context) {
    val intent = Intent(context, MainComposeActivity::class.java)
    (context as FragmentActivity).finish()
    context.startActivity(intent)
    context.finishAffinity()
}

@Composable
fun ThemeComposable(index: Int, onClick: () -> Unit) {
    var chosenTheme by remember {
        mutableStateOf(false)
    }
    val image = when (index) {
        0 -> R.drawable.red_theme
        1 -> R.drawable.dynamic_theme
        2 -> R.drawable.green_theme
        else -> {
            R.drawable.blue_theme
        }
    }

    Column {
        Box(
            Modifier
                .padding(end = 6.dp)
                .height(250.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { chosenTheme = true }) {
            Image(painter = painterResource(id = image), contentDescription = "")

            if (chosenTheme) {
                FloatingActionButton(
                    containerColor = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 10.dp, end = 10.dp)
                        .size(30.dp),
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

        Text(text = "Red", textAlign = TextAlign.Center)
    }
}