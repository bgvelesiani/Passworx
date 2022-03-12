package com.gvelesiani.passworx.ui.passwordDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gvelesiani.passworx.data.models.PasswordModel
import com.gvelesiani.passworx.databinding.BottomSheetPasswordDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PasswordDetailsBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: PasswordDetailsVM by viewModel()
    lateinit var binding: BottomSheetPasswordDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetPasswordDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val password = arguments?.getParcelable<PasswordModel>(PASSWORD)
        if (password != null) {
            setData(password)
            viewModel.decryptPassword(password = password.password)
        }
        setupObservers()
    }

    private fun setData(password: PasswordModel) {
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

    private fun setupObservers() {
        viewModel.viewState.observe(this, {
            it?.let { observeViewState(it) }
        })
    }

    companion object {
        const val TAG = "PasswordDetailsBottomSheet"
        private const val PASSWORD = "PASSWORD"
        private const val POSITION = "PARKING_FINE_POSITION"
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