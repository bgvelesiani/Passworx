package com.gvelesiani.passworx.uiCompose.masterPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.uiCompose.components.ErrorDialog
import com.gvelesiani.passworx.uiCompose.components.GeneralButton
import com.gvelesiani.passworx.uiCompose.components.ProgressIndicator
import com.gvelesiani.passworx.uiCompose.composeTheme.accentColor
import com.gvelesiani.passworx.uiCompose.composeTheme.secondaryTextColor
import com.gvelesiani.passworx.uiCompose.composeTheme.textColorDark
import com.gvelesiani.passworx.uiCompose.composeTheme.textColorLight
import org.koin.androidx.compose.getViewModel

@Composable
fun MasterPasswordScreen(
    navController: NavController,
    viewModel: MasterPasswordVM = getViewModel()
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val focusedIndicatorColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
    val textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
    var masterPassword by remember { mutableStateOf(TextFieldValue("")) }

    val uiState = remember { viewModel.uiState }.collectAsState()

    val fragmentActivity = LocalContext.current as FragmentActivity
    val context = LocalContext.current
    val biometrics = viewModel.getBiometrics()
    biometrics.setupBiometricPrompt(fragmentActivity, context) {
        if (it) {
            navController.navigate(Screen.Overview.route) {
                popUpTo(0)
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(150.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            color = textColor,
            text = "Access vault with your master Password",
            fontFamily = FontFamily(Font(R.font.medium)),
            fontSize = 26.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = masterPassword,
            singleLine = true,
            onValueChange = {
                masterPassword = it
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
                    text = "Master Password"
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        GeneralButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Go to vault"
        ) {
            viewModel.doesPasswordMatch(masterPassword.text)
        }

        Spacer(modifier = Modifier.height(30.dp))

        when (val state = uiState.value) {
            is MasterPasswordUiState.Loading -> {
                ProgressIndicator(isDisplayed = true)
            }
            is MasterPasswordUiState.PasswordMatchSuccess -> {
                navController.navigate(Screen.Overview.route) {
                    popUpTo(0)
                }
            }
            is MasterPasswordUiState.BiometricsAreAllowed -> {
                Image(
                    painter = painterResource(id = R.drawable.ic_fingerprint),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        biometrics.authenticate()
                    })
            }
            is MasterPasswordUiState.Empty -> {}
            is MasterPasswordUiState.Error -> {
                ErrorDialog(errorMsg = state.errorMsg)
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}