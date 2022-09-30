package com.gvelesiani.passworx.uiCompose.updatePassword

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.uiCompose.components.GeneralButton
import com.gvelesiani.passworx.uiCompose.components.ToolbarView
import com.gvelesiani.passworx.uiCompose.composeTheme.accentColor
import com.gvelesiani.passworx.uiCompose.composeTheme.secondaryTextColor
import com.gvelesiani.passworx.uiCompose.composeTheme.textColorDark
import com.gvelesiani.passworx.uiCompose.composeTheme.textColorLight
import org.koin.androidx.compose.getViewModel

@Composable
fun UpdatePasswordScreen(
    passwordModel: PasswordModel,
    navController: NavController,
    viewModel: UpdatePasswordVM = getViewModel()
) {
    val context = LocalContext.current
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val focusedIndicatorColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
    val textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight

    var title by remember { mutableStateOf(TextFieldValue(passwordModel.passwordTitle)) }
    var emailOrUserName by remember { mutableStateOf(TextFieldValue(passwordModel.emailOrUserName)) }
    var password by remember { mutableStateOf(TextFieldValue(viewModel.decryptPassword(passwordModel.password))) }
    var websiteOrAppName by remember { mutableStateOf(TextFieldValue(passwordModel.websiteOrAppName)) }
    var label by remember { mutableStateOf(TextFieldValue(passwordModel.label)) }

    val scrollState = rememberScrollState()

    Column(Modifier.fillMaxSize()) {
        ToolbarView(screenTitle = context.getString(R.string.title_update_password)) {
            navController.navigateUp()
        }
        Column(
            Modifier
                .verticalScroll(scrollState)
                .padding(bottom = 20.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                value = title,
                singleLine = true,
                onValueChange = {
                    title = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    errorIndicatorColor = accentColor,
                    focusedIndicatorColor = focusedIndicatorColor,
                    unfocusedIndicatorColor = secondaryTextColor,
                    cursorColor = accentColor,
                    textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                    placeholderColor = secondaryTextColor,
                    backgroundColor = Color.Transparent
                ),
                label = {
                    Text(
                        color = textColor,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontSize = 16.sp,
                        text = "Title"
                    )
                }
            )

            Text(
                color = textColor,
                text = "Password Details",
                modifier = Modifier.padding(top = 25.dp, start = 16.dp),
                fontFamily = FontFamily(Font(R.font.medium)),
                fontSize = 16.sp
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 10.dp),
                singleLine = true,
                value = emailOrUserName,
                onValueChange = {
                    emailOrUserName = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    errorIndicatorColor = accentColor,
                    focusedIndicatorColor = focusedIndicatorColor,
                    unfocusedIndicatorColor = secondaryTextColor,
                    cursorColor = accentColor,
                    textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                    placeholderColor = secondaryTextColor,
                    backgroundColor = Color.Transparent
                ),
                label = {
                    Text(
                        color = textColor,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontSize = 16.sp,
                        text = "Email or UserName*"
                    )
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 18.dp),
                value = password,
                singleLine = true,
                onValueChange = {
                    password = it
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.textFieldColors(
                    errorIndicatorColor = accentColor,
                    focusedIndicatorColor = focusedIndicatorColor,
                    unfocusedIndicatorColor = secondaryTextColor,
                    cursorColor = accentColor,
                    textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                    placeholderColor = secondaryTextColor,
                    backgroundColor = Color.Transparent
                ),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description, tint = textColor)
                    }
                },
                label = {
                    Text(
                        color = textColor,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontSize = 16.sp,
                        text = "Password*"
                    )
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 18.dp),
                singleLine = true,
                value = websiteOrAppName,
                onValueChange = {
                    websiteOrAppName = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    errorIndicatorColor = accentColor,
                    focusedIndicatorColor = focusedIndicatorColor,
                    unfocusedIndicatorColor = secondaryTextColor,
                    cursorColor = accentColor,
                    textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                    placeholderColor = secondaryTextColor,
                    backgroundColor = Color.Transparent
                ),
                label = {
                    Text(
                        color = textColor,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontSize = 16.sp,
                        text = "Website or App Name*"
                    )
                }
            )

            Text(
                modifier = Modifier.padding(top = 10.dp, start = 16.dp),
                text = "For example: Facebook, Snapchat, Pinterest...",
                color = secondaryTextColor,
                fontFamily = FontFamily(Font(R.font.italic))
            )

            Text(
                color = textColor,
                text = "Others",
                modifier = Modifier.padding(top = 25.dp, start = 16.dp),
                fontFamily = FontFamily(Font(R.font.medium)),
                fontSize = 16.sp
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 10.dp),
                singleLine = true,
                value = label,
                onValueChange = {
                    label = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    errorIndicatorColor = accentColor,
                    focusedIndicatorColor = focusedIndicatorColor,
                    unfocusedIndicatorColor = secondaryTextColor,
                    cursorColor = accentColor,
                    textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                    placeholderColor = secondaryTextColor,
                    backgroundColor = Color.Transparent
                ),
                label = {
                    Text(
                        color = textColor,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontSize = 16.sp,
                        text = "Label"
                    )
                }
            )

            GeneralButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 35.dp
                    ),
                text = "Update Password"
            ) {
                viewModel.updatePassword(
                    PasswordModel(
                        passwordId = passwordModel.passwordId,
                        password = viewModel.encryptPassword(password.text),
                        passwordTitle = title.text,
                        websiteOrAppName = websiteOrAppName.text,
                        emailOrUserName = emailOrUserName.text,
                        label = label.text,
                        isFavorite = passwordModel.isFavorite,
                        isInTrash = passwordModel.isInTrash
                    )
                )
                navController.navigateUp()
            }
        }
    }

}