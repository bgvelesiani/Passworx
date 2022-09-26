package com.gvelesiani.passworx.ui.passwords

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.gvelesiani.passworx.common.extensions.formatWebsite
import com.gvelesiani.passworx.common.util.OnLifecycleEvent
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.ui.components.*
import com.gvelesiani.passworx.ui.composeTheme.*
import com.gvelesiani.passworx.ui.passwordDetails.BottomSheet
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

    Box(
        Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) bgColorDark else bgColorLight)
    ) {
        Column(Modifier.fillMaxSize()) {

            ToolbarView(screenTitle = "Passwords") {
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
                            viewModel.decryptPassword(it.password)
                        },
                        onFavorite = {
                            viewModel.updateFavoriteState(
                                !it.isFavorite,
                                it.passwordId
                            )
                        },
                        onPassword = {

                        },
                        navController = navController
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
            Icon(imageVector = Icons.Filled.Add, contentDescription = "", tint = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PasswordContent(
    passwords: List<PasswordModel>,
    onCopy: (PasswordModel) -> Unit,
    onFavorite: (PasswordModel) -> Unit,
    onPassword: (PasswordModel) -> Unit,
    navController: NavController
) {
    var clickedPassword by remember {
        mutableStateOf(PasswordModel())
    }
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val coroutineScope = rememberCoroutineScope()

    val bgColor = if (isSystemInDarkTheme()) bgColorDark else bgColorLight

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = { BottomSheet(navController = navController, password = clickedPassword) },
        modifier = Modifier.fillMaxWidth(),
        sheetPeekHeight = 0.dp,
        backgroundColor = bgColor
    ) {
        LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
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
                        // TODO: Bottomsheet for password details
                        clickedPassword = it
                        coroutineScope.launch {
                            if (sheetState.isCollapsed) sheetState.expand()
                            else sheetState.collapse()
                        }
                    }
                )
            }
        }
    }
}