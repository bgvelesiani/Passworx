package com.gvelesiani.passworx.ui.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.common.util.OnLifecycleEvent
import com.gvelesiani.passworx.ui.components.EmptyListView
import com.gvelesiani.passworx.ui.components.PasswordItem
import com.gvelesiani.passworx.ui.components.ProgressIndicator
import com.gvelesiani.passworx.ui.components.SearchView
import com.gvelesiani.passworx.ui.components.ToolbarView
import org.koin.androidx.compose.getViewModel

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: PasswordFavoritesVM = getViewModel()
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
                is FavoritesUIState.Success -> {
                    FavoritePasswordsContent(
                        passwords = state.passwords,
                        onCopy = {
                            viewModel.decryptPassword(it.password)
                        },
                        onFavorite = {
                            viewModel.updateFavoriteState(
                                passwordId = it.passwordId
                            )
                        },
                        onPassword = {

                        }
                    )
                }
                is FavoritesUIState.Empty -> {
                    EmptyListView()
                }
                is FavoritesUIState.Loading -> {
                    ProgressIndicator(isDisplayed = true)
                }
                is FavoritesUIState.Error -> {}
            }
        }
    }
}

@Composable
fun FavoritePasswordsContent(
    passwords: List<PasswordModel>,
    onCopy: (PasswordModel) -> Unit,
    onFavorite: (PasswordModel) -> Unit,
    onPassword: (PasswordModel) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
        items(passwords) { password ->
            PasswordItem(
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