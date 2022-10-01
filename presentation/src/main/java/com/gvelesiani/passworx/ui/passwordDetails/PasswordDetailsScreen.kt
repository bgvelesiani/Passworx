package com.gvelesiani.passworx.ui.passwordDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.ui.components.GeneralButton
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordDetailsScreen(
    navController: NavController,
    viewModel: PasswordDetailsVM = getViewModel(),
    password: PasswordModel,
    onDelete: (PasswordModel) -> Unit,
    onUpdate: () -> Unit
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
//    val textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
//    val bgColor = if (isSystemInDarkTheme()) bottomSheetBgDark else bottomSheetBgLight

    if (password.password != "") {
        viewModel.decryptPassword(password.password)
    }
    val decryptedPassword = remember { viewModel.decryptedPassword }.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                modifier = Modifier,
                text = password.passwordTitle,
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                textAlign = TextAlign.Center,
            )
            Column(
                Modifier
                    .padding(end = 10.dp)
                    .width(40.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onDelete.invoke(password)
                    }
            ) {
                Icon(
                    painterResource(
                        id = R.drawable.ic_trash
                    ),
                    ""
                )
            }
        }
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
                .padding(top = 8.dp, start = 15.dp, end = 15.dp),
            value = decryptedPassword.value,
            enabled = false,
            onValueChange = {},
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            colors = TextFieldDefaults.textFieldColors(
//                textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight,
//                backgroundColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent
//            ),
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
                .padding(top = 20.dp, start = 30.dp, end = 30.dp, bottom = 30.dp),
            text = "Update Password",
        ) {
            onUpdate.invoke()
            navController.currentBackStackEntry?.savedStateHandle?.set(
                key = "password",
                value = password
            )
            navController.navigate(Screen.UpdatePassword.route)
        }
    }
}