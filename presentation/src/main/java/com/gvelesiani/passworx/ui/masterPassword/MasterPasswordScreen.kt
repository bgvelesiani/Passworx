package com.gvelesiani.passworx.ui.masterPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.gvelesiani.passworx.ui.components.ErrorDialog
import com.gvelesiani.passworx.ui.components.GeneralButton
import com.gvelesiani.passworx.ui.components.ProgressIndicator
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterPasswordScreen(
    navController: NavController,
    viewModel: MasterPasswordVM = getViewModel()
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var masterPassword by remember { mutableStateOf(TextFieldValue("")) }

    val biometricsAreAllowed = remember {
        viewModel.biometricsAreAllowed
    }.collectAsState()

    val uiState = remember { viewModel.uiState }.collectAsState()

    val fragmentActivity = LocalContext.current as FragmentActivity
    val context = LocalContext.current
    val biometrics = viewModel.getBiometrics()
    biometrics.setupBiometricPrompt(fragmentActivity, context){
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
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            label = {
                Text(
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

            is MasterPasswordUiState.Empty -> {}
            is MasterPasswordUiState.Error -> {
                ErrorDialog(errorMsg = state.errorMsg)
            }
        }

        if (biometricsAreAllowed.value) {
            Image(
                painter = painterResource(id = R.drawable.ic_fingerprint),
                contentDescription = "",
                modifier = Modifier.clickable {
                    biometrics.authenticate(
                        context.getString(com.gvelesiani.helpers.R.string.biometric_auth_subtitle),
                        context.getString(com.gvelesiani.helpers.R.string.biometric_auth_negative_button_text)
                    )
                })
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}