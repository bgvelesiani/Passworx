package com.gvelesiani.passworx.uiCompose.masterPassword.createMasterPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.uiCompose.composeTheme.*
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateMasterPasswordScreen(
    navController: NavController,
    viewModel: CreateMasterPasswordVM = getViewModel()
) {
    var masterPassword by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val focusedIndicatorColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
    val textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight

    val chipBgColor = if (isSystemInDarkTheme()) chipBgColorDark else chipBgColorLight
    val chipTextColor = if (isSystemInDarkTheme()) chipTextColorDark else chipTextColorLight
    val chipCheckedBgColor =
        if (isSystemInDarkTheme()) chipBgColorCheckedDark else chipBgColorCheckedLight
    val chipCheckedTextColor =
        if (isSystemInDarkTheme()) chipTextColorCheckedDark else chipTextColorCheckedLight

    val fabIsEnabled = remember { mutableStateOf(false) }

    val validationErrors = remember {
        viewModel.validationErrors
    }.collectAsState()

    val passwordCreated = remember {
        viewModel.passwordCreated
    }.collectAsState()

    val isValidPassword = remember {
        viewModel.isValid
    }.collectAsState()

    Scaffold(Modifier.background(if (isSystemInDarkTheme()) bgColorDark else bgColorLight)) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                color = textColor,
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Let's get started!",
                fontFamily = FontFamily(Font(R.font.medium)),
                fontSize = 28.sp
            )

            Text(
                color = secondaryTextColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = "Please create a secure master password including the following criteria below.",
                fontFamily = FontFamily(Font(R.font.regular)),
                fontSize = 16.sp
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
                value = masterPassword,
                singleLine = true,
                onValueChange = {
                    masterPassword = it
                    viewModel.validate(masterPassword = it.text)
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.textFieldColors(
                    errorIndicatorColor = accentColor,
                    focusedIndicatorColor = focusedIndicatorColor,
                    unfocusedIndicatorColor = secondaryTextColor,
                    cursorColor = accentColor,
                    textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                    placeholderColor = secondaryTextColor,
                    backgroundColor = Color.Transparent
                ),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description, tint = textColor)
                    }
                },
                label = {
                    Text(
                        color = textColor,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontSize = 16.sp,
                        text = "Master password"
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            FlowRow(modifier = Modifier.fillMaxWidth()) {
                validationErrors.value.forEach { masterPasswordError ->
                    Chip(modifier = Modifier.padding(end = 16.dp),
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (!masterPasswordError.isValid) chipBgColor else chipCheckedBgColor,
                        ), onClick = {}) {
                        Text(
                            color = if (!masterPasswordError.isValid) chipTextColor else chipCheckedTextColor,
                            text = masterPasswordError.error
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(Modifier.fillMaxWidth()) {
                FloatingActionButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
                    backgroundColor = accentColor,
                    onClick = {
                        viewModel.createMasterPassword(masterPassword = masterPassword.text)
                    }) {
                    Icon(
                        painterResource(
                            id = R.drawable.ic_overview_item_arrow
                        ),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }

            if (passwordCreated.value) {
                navController.navigate(Screen.MasterPassword.route) {
                    popUpTo(0)
                }
            }

            fabIsEnabled.value = isValidPassword.value
        }
    }
}