package com.gvelesiani.passworx.uiCompose.trash

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.common.extensions.formatWebsite
import com.gvelesiani.passworx.common.util.OnLifecycleEvent
import com.gvelesiani.passworx.uiCompose.components.*
import com.gvelesiani.passworx.uiCompose.composeTheme.bgColorDark
import com.gvelesiani.passworx.uiCompose.composeTheme.bgColorLight
import org.koin.androidx.compose.getViewModel

@Composable
fun TrashScreen(
    navController: NavController,
    viewModel: PasswordTrashVM = getViewModel()
) {
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
                is TrashUIState.Success -> {
                    TrashedPasswordsContent(
                        passwords = state.passwords,
                        onCopy = {},
                        onFavorite = {},
                        onPassword = {}
                    )
                }
                is TrashUIState.Empty -> {
                    EmptyListView()
                }
                is TrashUIState.Loading -> {
                    ProgressIndicator(isDisplayed = true)
                }
                is TrashUIState.Error -> {}
            }
        }
    }
}

@Composable
fun TrashedPasswordsContent(
    passwords: List<PasswordModel>,
    onCopy: (PasswordModel) -> Unit,
    onFavorite: (PasswordModel) -> Unit,
    onPassword: (PasswordModel) -> Unit
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
                }
            )
        }
    }
}