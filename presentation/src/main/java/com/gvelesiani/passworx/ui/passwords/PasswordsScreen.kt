package com.gvelesiani.passworx.ui.passwords

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.gvelesiani.common.models.PassworxColors
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.helpers.helpers.biometrics.BiometricsHelper
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.common.extensions.copyToClipboard
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.ui.ThemeSharedVM
import com.gvelesiani.passworx.ui.components.EmptyListView
import com.gvelesiani.passworx.ui.components.ErrorDialog
import com.gvelesiani.passworx.ui.components.GeneralButton
import com.gvelesiani.passworx.ui.components.PasswordItem
import com.gvelesiani.passworx.ui.components.ProgressIndicator
import com.gvelesiani.passworx.ui.components.SearchView
import com.gvelesiani.passworx.ui.components.ToolbarView
import com.gvelesiani.passworx.ui.masterPassword.MasterPasswordUiState
import com.gvelesiani.passworx.ui.masterPassword.MasterPasswordVM
import com.gvelesiani.passworx.ui.theme.RedThemeDarkColors
import com.gvelesiani.passworx.ui.theme.RedThemeLightColors
import com.gvelesiani.passworx.ui.theme.blue.BlueThemeDarkColors
import com.gvelesiani.passworx.ui.theme.blue.BlueThemeLightColors
import com.gvelesiani.passworx.ui.theme.green.GreenThemeDarkColors
import com.gvelesiani.passworx.ui.theme.green.GreenThemeLightColors
import com.gvelesiani.passworx.ui.theme.supportsDynamic
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class
)
@Composable
fun PasswordsScreen(
    navController: NavController,
    viewModel: PasswordsVM = getViewModel(),
    sharedVM: ThemeSharedVM = getViewModel()
) {
    sharedVM.getCurrentAppTheme(supportsDynamic())
    val uiState = remember { viewModel.uiState }.collectAsState()
    val biometricsAreAllowed = remember {
        viewModel.biometricsAreAllowed
    }.collectAsState()

    val passwordModel = remember {
        viewModel.passwodModel
    }.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val fragmentActivity = LocalContext.current as FragmentActivity
    val biometrics = viewModel.getBiometrics()

    val currentThemeColors = remember {
        sharedVM.currentThemeColors
    }.collectAsState()

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)


    ModalBottomSheetLayout(
        sheetContent = {
            MasterPasswordContent(model = passwordModel.value, navController = navController) {
                coroutineScope.launch {
                    bottomSheetState.hide()
                }
            }
        },
        sheetState = bottomSheetState
    ) {
        Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth(),
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                ) {
                    Column(Modifier.fillMaxSize()) {
                        ToolbarView(screenTitle = context.getString(R.string.title_passwords)) {
                            navController.navigateUp()
                        }

                        SearchView {
                            viewModel.searchPasswords(it)
                        }

                        Spacer(modifier = Modifier.height(15.dp))

                        when (val state = uiState.value) {
                            is PasswordsUIState.Success -> {
                                PasswordContent(
                                    passworxColors = currentThemeColors.value,
                                    passwords = state.passwords,
                                    onCopy = {
                                        if (biometricsAreAllowed.value) {
                                            biometrics.authenticate(
                                                subTitle = "Copy Password",
                                                negativeButtonText = "Cancel"
                                            )
                                            setupBiometricAuthentication(
                                                biometricsHelper = biometrics,
                                                fragmentActivity = fragmentActivity,
                                                context = context,
                                                onSuccess = {
                                                    coroutineScope.launch {
                                                        val password =
                                                            viewModel.decryptPassword(it.password)
                                                        password.copyToClipboard(context)
                                                        snackbarHostState.showSnackbar("Password copied successfully")
                                                    }
                                                }
                                            )
                                        } else {
                                            coroutineScope.launch {
                                                bottomSheetState.show()
                                            }
                                        }
                                    },
                                    onFavorite = { passwordModel ->
                                        viewModel.updateFavoriteState(
                                            !passwordModel.isFavorite,
                                            passwordModel.passwordId
                                        )
                                    },
                                    onPassword = { passwordModel ->
                                        viewModel.setPasswordModel(passwordModel)

                                        if (biometricsAreAllowed.value) {
                                            biometrics.authenticate(
                                                subTitle = "Update Password",
                                                negativeButtonText = "Cancel"
                                            )
                                            setupBiometricAuthentication(
                                                biometricsHelper = biometrics,
                                                fragmentActivity = fragmentActivity,
                                                context = context,
                                                onSuccess = {
                                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                                        key = "password",
                                                        value = passwordModel
                                                    )
                                                    navController.navigate(Screen.Details.route)
                                                }
                                            )
                                        } else {
                                            coroutineScope.launch {
                                                bottomSheetState.show()
                                            }
                                        }
                                    }
                                )
                            }

                            is PasswordsUIState.Empty -> {
                                EmptyListView()
                            }

                            is PasswordsUIState.Loading -> {
                                ProgressIndicator(isDisplayed = true)
                            }

                            is PasswordsUIState.Error -> {}
                        }
                    }

                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp, bottom = 16.dp)
                            .size(56.dp),
                        onClick = {
                            navController.navigate(Screen.AddNewPassword.route)
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

fun setupBiometricAuthentication(
    biometricsHelper: BiometricsHelper,
    fragmentActivity: FragmentActivity,
    context: Context,
    onSuccess: () -> Unit
) {
    biometricsHelper.setupBiometricPrompt(fragmentActivity, context) {
        if (it) {
            onSuccess.invoke()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterPasswordContent(
    viewModel: MasterPasswordVM = getViewModel(),
    model: PasswordModel,
    navController: NavController,
    dismiss: () -> Unit
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var masterPassword by remember { mutableStateOf(TextFieldValue("")) }
    val uiState = remember { viewModel.uiState }.collectAsState()

    Column(
        Modifier
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Please enter your master password",
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
            text = "Go to details"
        ) {
            dismiss.invoke()
            viewModel.doesPasswordMatch(masterPassword.text)
        }

        Spacer(modifier = Modifier.height(30.dp))

        when (val state = uiState.value) {
            is MasterPasswordUiState.Loading -> {
                ProgressIndicator(isDisplayed = true)
            }

            is MasterPasswordUiState.PasswordMatchSuccess -> {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "password",
                    value = model
                )
                navController.navigate(Screen.Details.route)
                viewModel.setEmptyUiState()

            }

            is MasterPasswordUiState.Empty -> {}
            is MasterPasswordUiState.Error -> {
                ErrorDialog(errorMsg = state.errorMsg) {
                    viewModel.setEmptyUiState()
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun PasswordContent(
    passworxColors: PassworxColors,
    passwords: List<PasswordModel>,
    onCopy: (PasswordModel) -> Unit,
    onFavorite: (PasswordModel) -> Unit,
    onPassword: (PasswordModel) -> Unit
) {
    val context = LocalContext.current
    val titleContainerColor = when (passworxColors) {
        PassworxColors.Dynamic -> {
            if (isSystemInDarkTheme()) dynamicDarkColorScheme(context).primary else dynamicLightColorScheme(
                context
            ).primary
        }

        PassworxColors.Red -> {
            if (isSystemInDarkTheme()) RedThemeDarkColors.primary else RedThemeLightColors.primary
        }

        PassworxColors.Green -> {
            if (isSystemInDarkTheme()) GreenThemeDarkColors.primary else GreenThemeLightColors.primary
        }

        PassworxColors.Blue -> {
            if (isSystemInDarkTheme()) BlueThemeDarkColors.primary else BlueThemeLightColors.primary
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp),
        modifier = Modifier.animateContentSize()
    ) {
        items(passwords) { password ->
            PasswordItem(
                titleContainerColor = titleContainerColor,
                password = password,
                onCopyClick = {
                    onCopy.invoke(password)
                },
                onFavoriteClick = {
                    onFavorite.invoke(password)
                },
                onPasswordClick = {
                    onPassword.invoke(password)
                }
            )
        }
    }
}