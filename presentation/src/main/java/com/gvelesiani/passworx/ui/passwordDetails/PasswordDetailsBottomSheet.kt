package com.gvelesiani.passworx.ui.passwordDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.gvelesiani.base.BaseBottomSheet
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.databinding.BottomSheetPasswordDetailsBinding
import com.gvelesiani.passworx.ui.composeTheme.textColorDark
import com.gvelesiani.passworx.ui.composeTheme.textColorLight

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
        val textColor = if(isSystemInDarkTheme()) textColorDark else textColorLight
        Column {
            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = password.passwordTitle,
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(
                    R.font.medium, FontWeight.Normal
                )),
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