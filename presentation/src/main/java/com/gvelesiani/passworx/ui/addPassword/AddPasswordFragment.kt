package com.gvelesiani.passworx.ui.addPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gvelesiani.base.BaseFragment
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.common.extensions.onTextChanged
import com.gvelesiani.passworx.databinding.FragmentAddPasswordBinding
import com.gvelesiani.passworx.ui.components.ToolbarView
import com.gvelesiani.passworx.ui.composeTheme.accentColor
import com.gvelesiani.passworx.ui.composeTheme.secondaryTextColor
import com.gvelesiani.passworx.ui.composeTheme.textColorDark
import com.gvelesiani.passworx.ui.composeTheme.textColorLight


class AddPasswordFragment :
    BaseFragment<AddPasswordVM, FragmentAddPasswordBinding>(AddPasswordVM::class) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddPasswordBinding =
        FragmentAddPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        binding.toolbar.setupToolbar {
            findNavController().navigateUp()
        }
        watchFields()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btAddNewPassword.setOnClickListener {
            addNewPassword()
        }
    }

    private fun addNewPassword() {
        with(binding) {
            viewModel.addNewPassword(
                com.gvelesiani.common.models.domain.PasswordModel(
                    password = viewModel.encryptPassword(etPassword.editText?.text.toString()),
                    passwordTitle = etTitle.editText?.text.toString(),
                    websiteOrAppName = etWebsiteOrAppName.editText?.text.toString(),
                    emailOrUserName = etEmailOrUserName.editText?.text.toString(),
                    label = etLabel.editText?.text.toString()
                )
            )
        }
        findNavController().navigateUp()
    }

    private fun watchFields() {
        binding.etTitle.editText?.onTextChanged {
            viewModel.onTitleChanged(it)
        }
        binding.etLabel.editText?.onTextChanged {
            viewModel.onLabelChanged(it)
        }
    }

    @Composable
    fun AddPasswordContent() {
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        val focusedIndicatorColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
        val textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight

        var title by remember { mutableStateOf(TextFieldValue("")) }
        var emailOrUserName by remember { mutableStateOf(TextFieldValue("")) }
        var password by remember { mutableStateOf(TextFieldValue("")) }
        var websiteOrAppName: String = ""
        var label by remember { mutableStateOf(TextFieldValue("")) }

        val scrollState = rememberScrollState()

        Column(
            Modifier
                .fillMaxSize()
                .scrollable(scrollState, Orientation.Vertical)
        ) {
            ToolbarView(screenTitle = "Add New Password") {
                findNavController().navigateUp()
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 15.dp),
                value = title,
                onValueChange = {
                    title = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    errorIndicatorColor = accentColor,
                    focusedIndicatorColor = focusedIndicatorColor,
                    unfocusedIndicatorColor = secondaryTextColor,
                    cursorColor = accentColor,
                    textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                    placeholderColor = secondaryTextColor
                ),
                placeholder = {
                    Text(
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontSize = 16.sp,
                        text = "Title"
                    )
                }
            )

            Text(
                color = textColor,
                text = "Password Details",
                modifier = Modifier.padding(top = 15.dp),
                fontFamily = FontFamily(Font(R.font.medium)),
                fontSize = 16.sp
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 15.dp),
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
                    placeholderColor = secondaryTextColor
                ),
                placeholder = {
                    Text(
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontSize = 16.sp,
                        text = "Email or UserName*"
                    )
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 20.dp),
                value = password,
                onValueChange = {
                    password = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    errorIndicatorColor = accentColor,
                    focusedIndicatorColor = focusedIndicatorColor,
                    unfocusedIndicatorColor = secondaryTextColor,
                    cursorColor = accentColor,
                    textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                    placeholderColor = secondaryTextColor
                ),
                placeholder = {
                    Text(
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontSize = 16.sp,
                        text = "Password*"
                    )
                }
            )

            CustomField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 20.dp),
                placeHolderText = "Website or App Name*", value = {
                    websiteOrAppName = it.text
                },
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                })

//            OutlinedTextField(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 16.dp, end = 16.dp, top = 20.dp),
//                value = websiteOrAppName,
//                onValueChange = {
//                    websiteOrAppName = it
//                },
//                colors = TextFieldDefaults.textFieldColors(
//                    errorIndicatorColor = accentColor,
//                    focusedIndicatorColor = focusedIndicatorColor,
//                    unfocusedIndicatorColor = secondaryTextColor,
//                    cursorColor = accentColor,
//                    textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight,
//                    placeholderColor = secondaryTextColor
//                ),
//                placeholder = {
//                    Text(
//                        fontFamily = FontFamily(Font(R.font.regular)),
//                        fontSize = 16.sp,
//                        text = "Website or App Name*"
//                    )
//                }
//            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 20.dp),
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
                    placeholderColor = secondaryTextColor
                ),
                placeholder = {
                    Text(
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
                    viewModel.addNewPassword(
                        PasswordModel(
                            password = viewModel.encryptPassword(password.text),
                            passwordTitle = title.text,
                            websiteOrAppName = websiteOrAppName,
                            emailOrUserName = emailOrUserName.text,
                            label = label.text
                        )
                    )
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

    @Composable
    fun CustomField(
        placeHolderText: String,
        modifier: Modifier,
        value: (TextFieldValue) -> Unit,
        trailingIcon: @Composable () -> Unit
    ) {
        val focusedIndicatorColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
        var fieldValue by remember { mutableStateOf(TextFieldValue("")) }

        OutlinedTextField(
            trailingIcon = {
                trailingIcon.invoke()
            },
            modifier = modifier,
            value = fieldValue,
            onValueChange = {
                fieldValue = it
                value.invoke(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                errorIndicatorColor = accentColor,
                focusedIndicatorColor = focusedIndicatorColor,
                unfocusedIndicatorColor = secondaryTextColor,
                cursorColor = accentColor,
                textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                placeholderColor = secondaryTextColor
            ),
            placeholder = {
                Text(
                    fontFamily = FontFamily(Font(R.font.regular)),
                    fontSize = 16.sp,
                    text = placeHolderText
                )
            }
        )
    }

    @Preview
    @Composable
    fun AddPasswordContentPreview() {
        AddPasswordScreen()
    }

    private fun observeViewState(viewState: AddPasswordVM.ViewState) {
        if (viewState.showAddNewPasswordError != null) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.generate_password_error_dialog_title))
                .setMessage(viewState.showAddNewPasswordError)
                .setPositiveButton(resources.getString(R.string.generate_password_error_dialog_button_text)) { _, _ ->
                }
                .show()
        }

        binding.etTitle.error = viewState.showTitleErrorMessage
        binding.etLabel.error = viewState.showLabelErrorMessage
        binding.btAddNewPassword.isEnabled = viewState.addButtonEnabled
    }

    override fun setupObservers() {
        viewModel.viewState.observe(this) {
            it?.let { observeViewState(it) }
        }
    }
}