package com.gvelesiani.passworx.ui.addPassword

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.ui.components.ToolbarView
import com.gvelesiani.passworx.ui.composeTheme.accentColor
import com.gvelesiani.passworx.ui.composeTheme.secondaryTextColor
import com.gvelesiani.passworx.ui.composeTheme.textColorDark
import com.gvelesiani.passworx.ui.composeTheme.textColorLight

@Preview
@Composable
fun AddPasswordScreen() {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val focusedIndicatorColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
    val textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight

    var title by remember { mutableStateOf(TextFieldValue("")) }
    var emailOrUserName by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var websiteOrAppName by remember { mutableStateOf(TextFieldValue("")) }
    var label by remember { mutableStateOf(TextFieldValue("")) }

    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .scrollable(scrollState, Orientation.Vertical)
    ) {
        ToolbarView(screenTitle = "Add New Password") {
//            findNavController().navigateUp()
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 15.dp),
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
            modifier = Modifier.padding(top = 18.dp, start = 16.dp),
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
            modifier = Modifier.padding(top = 18.dp, start = 16.dp),
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

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 20.dp
                ),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = accentColor
            ),
            onClick = {
//                viewModel.addNewPassword(
//                    PasswordModel(
//                        password = viewModel.encryptPassword(password.text),
//                        passwordTitle = title.text,
//                        websiteOrAppName = websiteOrAppName,
//                        emailOrUserName = emailOrUserName.text,
//                        label = label.text
//                    )
//                )
            }) {
            Text(
                modifier = Modifier.padding(top = 7.dp, bottom = 7.dp),
                text = "Add Password",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.regular))
            )
        }
    }
}