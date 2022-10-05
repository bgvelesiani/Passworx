package com.gvelesiani.passworx.ui.passwords

import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.common.extensions.formatWebsite
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.ui.components.EmptyListView
import com.gvelesiani.passworx.ui.components.PasswordItem
import com.gvelesiani.passworx.ui.components.ProgressIndicator
import com.gvelesiani.passworx.ui.components.SearchView
import com.gvelesiani.passworx.ui.components.ToolbarView
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun PasswordsScreen(navController: NavController, viewModel: PasswordsVM = getViewModel()) {
    val uiState = remember { viewModel.uiState }.collectAsState()

    val scaffoldState = rememberScaffoldState()

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    androidx.compose.material3.Scaffold {
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
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        key = "password",
                                        value = passwordModel
                                    )
                                    navController.navigate(Screen.Details.route)
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