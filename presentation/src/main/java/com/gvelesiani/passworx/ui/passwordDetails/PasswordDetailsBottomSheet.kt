package com.gvelesiani.passworx.ui.passwordDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.gvelesiani.base.BaseBottomSheet
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.databinding.BottomSheetPasswordDetailsBinding
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.ui.components.GeneralButton
import com.gvelesiani.passworx.ui.composeTheme.bgColorDark
import com.gvelesiani.passworx.ui.composeTheme.bgColorLight
import com.gvelesiani.passworx.ui.composeTheme.textColorDark
import com.gvelesiani.passworx.ui.composeTheme.textColorLight
import org.koin.androidx.compose.getViewModel

class PasswordDetailsBottomSheet :
    BaseBottomSheet<PasswordDetailsVM, BottomSheetPasswordDetailsBinding>(
        PasswordDetailsVM::class
    ) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetPasswordDetailsBinding
        get() = BottomSheetPasswordDetailsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        val password =
            arguments?.getParcelable<PasswordModel>(PASSWORD)
        if (password != null) {
            setData(password)
            viewModel.decryptPassword(password = password.password)
            setOnClickListeners(password)
        }
    }

    override fun setupObservers() {
        viewModel.viewState.observe(this) {
            it?.let { observeViewState(it) }
        }
    }


    @Composable
    fun PasswordDetailsContent(password: PasswordModel) {
        val textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
        Column {
            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = password.passwordTitle,
                fontSize = 22.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.medium, FontWeight.Normal
                    )
                ),
                color = textColor
            )

        }
    }

    private fun setOnClickListeners(password: PasswordModel) {
        binding.btUpdatePassword.setOnClickListener {
//            findNavController().navigate(
//            )
            this.dismiss()
        }
    }

    private fun setData(password: com.gvelesiani.common.models.domain.PasswordModel) {
        with(binding) {
            tvPassword.editText?.isFocusable = false
            tvEmailOrUsername.text = password.emailOrUserName
            tvWebsiteOrAppName.text = password.websiteOrAppName
            tvPasswordName.text = password.passwordTitle
        }
    }

    private fun observeViewState(viewState: PasswordDetailsVM.ViewState) {
        binding.tvPassword.editText?.setText(viewState.password)
    }

    companion object {
        const val TAG = "PasswordDetailsBottomSheet"
        private const val PASSWORD = "PASSWORD"
        fun show(
            password: PasswordModel,
            fragmentManager: FragmentManager,
            tag: String
        ) {
            PasswordDetailsBottomSheet().apply {
                arguments = bundleOf(PASSWORD to password)
                show(fragmentManager, tag)
            }
        }
    }
}

@Composable
fun BottomSheet(
    navController: NavController,
    viewModel: PasswordDetailsVM = getViewModel(),
    password: PasswordModel
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
    val bgColor = if (isSystemInDarkTheme()) bgColorDark else bgColorLight

    if(password.password != ""){
        viewModel.decryptPassword(password.password)
    }
    val pass = viewModel.viewState.value?.password

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            text = password.passwordTitle,
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.medium)),
            textAlign = TextAlign.Center,
            color = textColor
        )
        Text(
            modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp),
            text = "Email or Username*",
            fontFamily = FontFamily(Font(R.font.medium)),
            color = textColor,
            fontSize = 15.sp
        )
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 30.dp, end = 30.dp),
            text = password.emailOrUserName,
            fontFamily = FontFamily(Font(R.font.regular)),
            color = textColor,
            fontSize = 14.sp
        )
        Text(
            modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp),
            text = "Password*",
            fontFamily = FontFamily(Font(R.font.medium)),
            color = textColor,
            fontSize = 15.sp
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 15.dp, end = 15.dp),
            value = pass ?: "",
            enabled = false,
            onValueChange = {},
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                backgroundColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description, tint = textColor)
                }
            }
        )
        Text(
            modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp),
            text = "Website or App Name*",
            fontFamily = FontFamily(Font(R.font.medium)),
            color = textColor,
            fontSize = 15.sp
        )
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 30.dp, end = 30.dp),
            text = password.websiteOrAppName,
            fontFamily = FontFamily(Font(R.font.regular)),
            color = textColor,
            fontSize = 14.sp
        )
        GeneralButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp, end = 30.dp, bottom = 30.dp),
            text = "Update Password",
        ) {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                key = "passwordToEdit",
                value = password
            )
            navController.navigate(Screen.UpdatePassword.route)
        }
    }
}