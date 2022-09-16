package com.gvelesiani.passworx.ui.masterPassword

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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.ui.components.GeneralButton
import com.gvelesiani.passworx.ui.components.ProgressIndicator
import com.gvelesiani.passworx.ui.composeTheme.accentColor
import com.gvelesiani.passworx.ui.composeTheme.secondaryTextColor
import com.gvelesiani.passworx.ui.composeTheme.textColorDark
import com.gvelesiani.passworx.ui.composeTheme.textColorLight
import com.gvelesiani.passworx.ui.masterPassword.fragments.MasterPasswordUiState
import com.gvelesiani.passworx.ui.masterPassword.fragments.MasterPasswordVM
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
            is MasterPasswordUiState.PasswordMatchError -> {
                ProgressIndicator(isDisplayed = false)
            }
            is MasterPasswordUiState.BiometricsAreAllowed -> {}
            is MasterPasswordUiState.EmptyState -> {}
        }
        Spacer(modifier = Modifier.height(100.dp))

//        IconButton(
//            modifier = Modifier
//                .height(56.dp)
//                .width(56.dp),
//            onClick = {
//            }) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_fingerprint),
//                tint = if (isSystemInDarkTheme()) textColorDark else textColorLight,
//                contentDescription = "Back click area"
//            )
//        }
    }
}