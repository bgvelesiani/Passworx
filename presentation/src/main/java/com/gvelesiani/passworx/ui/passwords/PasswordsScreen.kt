package com.gvelesiani.passworx.ui.passwords

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.gvelesiani.passworx.OnLifecycleEvent
import com.gvelesiani.passworx.common.extensions.formatWebsite
import com.gvelesiani.passworx.common.extensions.getVM
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.ui.components.*
import com.gvelesiani.passworx.ui.composeTheme.*
import org.koin.core.annotation.KoinInternalApi

@OptIn(KoinInternalApi::class)
@Composable
fun PasswordsScreen(navController: NavController) {
    val viewModel = getVM<PasswordsVM>()
    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                viewModel.getPasswords()
            }
            else -> {}
        }
    }
    val passwords = remember { viewModel.passwords }.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

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

            ProgressIndicator(isDisplayed = isLoading.value)

            if (passwords.value.isEmpty() && !isLoading.value) {
                EmptyListView()
            } else {
                LazyColumn(contentPadding = PaddingValues(top = 15.dp, bottom = 80.dp)) {
                    items(passwords.value) { password ->
                        val logoResource = LocalContext.current.resources.getIdentifier(
                            password.websiteOrAppName.formatWebsite(),
                            "drawable",
                            "com.gvelesiani.passworx"
                        )
                        PasswordItem(
                            logoResource,
                            password = password,
                            onCopyClick = {
                                viewModel.decryptPassword(password.password)
                            },
                            onFavoriteClick = {
                                viewModel.updateFavoriteState(
                                    !password.isFavorite,
                                    password.passwordId
                                )
                            },
                            onPasswordClick = {
                                // TODO: Bottomsheet for password details
                            }
                        )
                    }
                }
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