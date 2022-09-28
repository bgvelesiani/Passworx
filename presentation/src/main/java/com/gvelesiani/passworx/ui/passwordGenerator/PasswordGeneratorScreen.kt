package com.gvelesiani.passworx.ui.passwordGenerator

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.common.extensions.copyToClipboard
import com.gvelesiani.passworx.ui.components.GeneralButton
import com.gvelesiani.passworx.ui.components.GeneratorSlider
import com.gvelesiani.passworx.ui.components.Switch
import com.gvelesiani.passworx.ui.components.ToolbarView
import com.gvelesiani.passworx.ui.composeTheme.*
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun PasswordGeneratorScreen(
    navController: NavController,
    viewModel: PasswordGeneratorVM = getViewModel()
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val selectedLength by remember { viewModel.selectedLength }.collectAsState()
    val generatedPassword by remember { viewModel.generatedPassword }.collectAsState()


    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier.padding(bottom = 90.dp),
            backgroundColor = if (isSystemInDarkTheme()) bgColorDark else bgColorLight
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
            ) {
                ToolbarView(screenTitle = context.getString(R.string.generate_password_title)) {
                    navController.navigateUp()
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(if (isSystemInDarkTheme()) generatePassBgColorDark else generatePassBgColorLight)
                ) {
                    Text(
                        modifier = Modifier.padding(15.dp),
                        fontFamily = FontFamily(Font(R.font.medium)),
                        text = generatedPassword,
                        fontSize = 22.sp,
                        color = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                        textAlign = TextAlign.Start
                    )
                }

                GeneratorSlider(
                    modifier = Modifier.padding(top = 10.dp),
                    selectedLength = selectedLength,
                    onValueChange = {
                        viewModel.onValueChanged(it)
                        viewModel.generatePassword(it.toInt())
                    }
                )

                Switch(
                    shouldBeChecked = true,
                    text = context.getString(R.string.generate_password_use_capital_letters_setting),
                    onCheck = {
                        viewModel.useCapitalLetters(it, selectedLength.toInt())
                    })
                Divider()
                Switch(
                    shouldBeChecked = true,
                    text = context.getString(R.string.generate_password_use_numbers_setting),
                    onCheck = {
                        viewModel.useNumbers(it, selectedLength.toInt())
                    })
                Divider()

                Switch(
                    shouldBeChecked = true,
                    text = context.getString(R.string.generate_password_use_capital_symbols_setting),
                    onCheck = {
                        viewModel.useSymbols(it, selectedLength.toInt())
                    })
            }
        }
        GeneralButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
            text = context.getString(R.string.copy_password_button_text)
        ) {
            generatedPassword.copyToClipboard(context)

            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message = context.getString(R.string.copy_password_snackbar_text))
            }
        }
    }
}
