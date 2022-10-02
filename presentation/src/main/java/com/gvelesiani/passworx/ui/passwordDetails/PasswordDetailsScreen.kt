package com.gvelesiani.passworx.ui.passwordDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.common.extensions.formatWebsite
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.ui.components.GeneralButton
import com.gvelesiani.passworx.ui.components.GeneralDialog
import com.gvelesiani.passworx.ui.components.ToolbarView
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordDetailsScreen(
    navController: NavController,
    viewModel: PasswordDetailsVM = getViewModel(),
    password: PasswordModel,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    if (password.password != "") {
        viewModel.decryptPassword(password.password)
    }
    val decryptedPassword = remember { viewModel.decryptedPassword }.collectAsState()

    val dialogState = remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            ToolbarView(screenTitle = "", imageVector = Icons.Filled.Close) {
                navController.navigateUp()
            }
            val logoResource = LocalContext.current.resources.getIdentifier(
                password.websiteOrAppName.formatWebsite(),
                "drawable",
                "com.gvelesiani.passworx"
            )

            Image(
                painter = painterResource(id = if (logoResource != 0) logoResource else R.drawable.apple),
                contentDescription = "",
                Modifier
                    .height(56.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = password.passwordTitle,
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                textAlign = TextAlign.Center,
            )

            Text(
                modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp),
                text = "Email or Username*",
                fontFamily = FontFamily(Font(R.font.medium)),
                fontSize = 15.sp
            )
            Text(
                modifier = Modifier.padding(top = 8.dp, start = 30.dp, end = 30.dp),
                text = password.emailOrUserName,
                fontFamily = FontFamily(Font(R.font.regular)),
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp),
                text = "Password*",
                fontFamily = FontFamily(Font(R.font.medium)),
                fontSize = 15.sp
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                value = decryptedPassword.value,
                enabled = false,
                onValueChange = {},
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            Text(
                modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp),
                text = "Website or App Name*",
                fontFamily = FontFamily(Font(R.font.medium)),
                fontSize = 15.sp
            )
            Text(
                modifier = Modifier.padding(top = 8.dp, start = 30.dp, end = 30.dp),
                text = password.websiteOrAppName,
                fontFamily = FontFamily(Font(R.font.regular)),
                fontSize = 14.sp
            )

            GeneralButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 30.dp, end = 30.dp, bottom = 30.dp),
                text = "Update Password",
            ) {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "password",
                    value = password
                )
                navController.navigate(Screen.UpdatePassword.route)
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        dialogState.value = true
                    },
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.medium)),
                text = "Delete password",
                fontSize = 16.sp
            )
        }

        GeneralDialog(
            title = "Delete password",
            text = "Do you really want to delete this password?",
            openDialog = dialogState
        ) {
            viewModel.deletePassword(
                isFavorite = password.isFavorite,
                passwordId = password.passwordId
            )
            navController.navigateUp()
        }
    }
}