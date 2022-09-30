package com.gvelesiani.passworx.uiCompose.masterPassword.changeMasterPassword

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.gvelesiani.passworx.uiCompose.components.ErrorDialog
import com.gvelesiani.passworx.uiCompose.components.GeneralButton
import com.gvelesiani.passworx.uiCompose.components.ProgressIndicator
import com.gvelesiani.passworx.uiCompose.components.ToolbarView
import com.gvelesiani.passworx.uiCompose.composeTheme.*
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChangeMasterPasswordScreen(
    navController: NavController,
    viewModel: ChangeMasterPasswordVM = getViewModel()
) {
    val validationErrors = remember { viewModel.validationErrors }.collectAsState()

    val uiState = remember { viewModel.uiState }.collectAsState()
    val enabled = remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var masterPassword by remember { mutableStateOf(TextFieldValue("")) }
    var newMasterPassword by remember { mutableStateOf(TextFieldValue("")) }

    var masterPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var newMasterPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val focusedIndicatorColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
    val textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight

    val chipBgColor = if (isSystemInDarkTheme()) chipBgColorDark else chipBgColorLight
    val chipTextColor = if (isSystemInDarkTheme()) chipTextColorDark else chipTextColorLight
    val chipCheckedBgColor =
        if (isSystemInDarkTheme()) chipBgColorCheckedDark else chipBgColorCheckedLight
    val chipCheckedTextColor =
        if (isSystemInDarkTheme()) chipTextColorCheckedDark else chipTextColorCheckedLight

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            ToolbarView(screenTitle = "") {
                navController.navigateUp()
            }
            Column(Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                Text(
                    color = textColor,
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Create new password",
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontSize = 28.sp
                )

                Text(
                    color = secondaryTextColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = "Your new password must be different from previous used password",
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
                    },
                    visualTransformation = if (masterPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                        val image = if (masterPasswordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description =
                            if (masterPasswordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { masterPasswordVisible = !masterPasswordVisible }) {
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

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp),
                    value = newMasterPassword,
                    singleLine = true,
                    onValueChange = {
                        newMasterPassword = it
                        viewModel.validateNewPassword(newMasterPassword.text)
                    },
                    visualTransformation = if (newMasterPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                        val image = if (newMasterPasswordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description =
                            if (newMasterPasswordVisible) "Hide password" else "Show password"

                        IconButton(onClick = {
                            newMasterPasswordVisible = !newMasterPasswordVisible
                        }) {
                            Icon(imageVector = image, description, tint = textColor)
                        }
                    },
                    label = {
                        Text(
                            color = textColor,
                            fontFamily = FontFamily(Font(R.font.regular)),
                            fontSize = 16.sp,
                            text = "New Master password"
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

                GeneralButton(
                    enabled = enabled.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = "Create master password"
                ) {
                    viewModel.validate(
                        masterPassword = masterPassword.text,
                        newMasterPassword = newMasterPassword.text
                    )
                }

                when (val state = uiState.value) {
                    is ChangePasswordUIState.Success -> {
                        LaunchedEffect(Unit) {
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(message = "Successfully changed master password")
                                navController.navigateUp()
                            }
                        }
                    }
                    is ChangePasswordUIState.Loading -> {
                        ProgressIndicator(true)
                    }
                    is ChangePasswordUIState.IsValid -> {
                        enabled.value = true
                    }
                    is ChangePasswordUIState.Error -> {
                        ErrorDialog(errorMsg = state.errorMsg)
                    }
                    is ChangePasswordUIState.Empty -> {}
                }
            }
        }
    }
}