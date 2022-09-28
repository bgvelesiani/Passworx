package com.gvelesiani.passworx.uiCompose.passwords

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.common.extensions.formatWebsite
import com.gvelesiani.passworx.common.util.OnLifecycleEvent
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.uiCompose.components.*
import com.gvelesiani.passworx.uiCompose.composeTheme.*
import com.gvelesiani.passworx.uiCompose.passwordDetails.PasswordDetailsScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PasswordsScreen(navController: NavController, viewModel: PasswordsVM = getViewModel()) {
    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                viewModel.getPasswords()
            }
            else -> {}
        }
    }

    val uiState = remember { viewModel.uiState }.collectAsState()

    val dialogState = remember {
        mutableStateOf(false)
    }

    var chosenPassword by remember {
        mutableStateOf(PasswordModel())
    }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val scaffoldState = rememberScaffoldState()

//    val decryptedPassword by viewModel.decryptedPassword.collectAsState("")

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        ModalBottomSheetLayout(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            scrimColor = Color.Transparent,
            sheetBackgroundColor = if (isSystemInDarkTheme()) bgSecondaryDark else bgSecondaryLight,
            sheetShape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
            sheetState = sheetState,
            sheetContent = {
                PasswordDetailsScreen(
                    navController = navController,
                    password = chosenPassword
                ) {
                    dialogState.value = true
                }
            }
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(if (isSystemInDarkTheme()) bgColorDark else bgColorLight)
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
                                passwords = state.passwords,
                                onCopy = {
//                                    viewModel.decryptPassword(it.password)
//                                    if (decryptedPassword != "") {
//                                        decryptedPassword.copyToClipboard(context)
                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Needs work"
                                        )
                                    }
                                },
                                onFavorite = { passwordModel ->
                                    viewModel.updateFavoriteState(
                                        !passwordModel.isFavorite,
                                        passwordModel.passwordId
                                    )
                                },
                                onPassword = { passwordModel ->
                                    chosenPassword = passwordModel
                                    coroutineScope.launch {
                                        if (!sheetState.isVisible) sheetState.show()
                                        else sheetState.hide()
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
                    backgroundColor = accentColor,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 16.dp)
                        .size(56.dp),
                    onClick = {
                        navController.navigate(Screen.AddNewPassword.route)
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }
    }
    GeneralDialog(
        title = "Delete password",
        text = "Do you really want to delete this password?",
        openDialog = dialogState
    ) {
        viewModel.updateItemTrashState(
            isFavorite = chosenPassword.isFavorite,
            passwordId = chosenPassword.passwordId
        )
        coroutineScope.launch {
            sheetState.hide()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PasswordContent(
    passwords: List<PasswordModel>,
    onCopy: (PasswordModel) -> Unit,
    onFavorite: (PasswordModel) -> Unit,
    onPassword: (PasswordModel) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp),
        modifier = Modifier.animateContentSize()
    ) {
        items(passwords) { password ->
            val logoResource = LocalContext.current.resources.getIdentifier(
                password.websiteOrAppName.formatWebsite(),
                "drawable",
                "com.gvelesiani.passworx"
            )
            PasswordItem(
                logoResource,
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