package com.gvelesiani.passworx.ui.passwordGenerator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.common.extensions.copyToClipboard
import com.gvelesiani.passworx.common.util.getPrimaryColorByTheme
import com.gvelesiani.passworx.common.util.getSurfaceVariantColorByTheme
import com.gvelesiani.passworx.ui.ThemeSharedVM
import com.gvelesiani.passworx.ui.components.GeneralButton
import com.gvelesiani.passworx.ui.components.Switch
import com.gvelesiani.passworx.ui.components.ToolbarView
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordGeneratorScreen(
    navController: NavController,
    viewModel: PasswordGeneratorVM = getViewModel(),
    themeVM: ThemeSharedVM = getViewModel()
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val selectedLength by remember { viewModel.selectedLength }.collectAsState()
    val generatedPassword by remember { viewModel.generatedPassword }.collectAsState()

    val currentThemeColors = remember {
        themeVM.currentThemeColors
    }.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
//            scaffoldState = scaffoldState,
            modifier = Modifier.padding(bottom = 90.dp)
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
                        .background(getSurfaceVariantColorByTheme(
                            colors = currentThemeColors.value,
                            context = context
                        ))
                ) {
                    val annotatedString = buildAnnotatedString {
                        for (char in generatedPassword) {
                            if (char.isDigit()) {
                                withStyle(style = SpanStyle(color = getPrimaryColorByTheme(
                                    context = context,
                                    colors = currentThemeColors.value
                                ))) {
                                    append(char)
                                }
                            } else {
                                append(char)
                            }
                        }
                    }

                    Text(
                        modifier = Modifier.padding(15.dp),
                        fontFamily = FontFamily(Font(R.font.medium)),
                        text = annotatedString,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Left
                    )
                }

                val passwordLengthSpan = buildAnnotatedString {
                    append("Generated password length: ")
                    withStyle(style = SpanStyle(color = getPrimaryColorByTheme(
                        context = context,
                        colors = currentThemeColors.value
                    ))) {
                        append(selectedLength.toInt().toString())
                    }
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    text = passwordLengthSpan,
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.medium, FontWeight.Normal))
                )

                Slider(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp),
                    value = selectedLength,
                    onValueChange = {
                        viewModel.onValueChanged(it)
                        viewModel.generatePassword(it.toInt())
                    },
                    valueRange = 8f..50f,
                    steps = 41
                )

                Switch(
                    shouldBeChecked = true,
                    text = context.getString(R.string.generate_password_use_capital_letters_setting),
                    onCheck = { useCapitalLetters ->
                        viewModel.useCapitalLetters(useCapitalLetters, selectedLength.toInt())
                    })

                Divider()

                Switch(
                    shouldBeChecked = true,
                    text = context.getString(R.string.generate_password_use_numbers_setting),
                    onCheck = { useNumbers ->
                        viewModel.useNumbers(useNumbers, selectedLength.toInt())
                    })

                Divider()

                Switch(
                    shouldBeChecked = true,
                    text = context.getString(R.string.generate_password_use_capital_symbols_setting),
                    onCheck = { useSymbols ->
                        viewModel.useSymbols(useSymbols, selectedLength.toInt())
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
