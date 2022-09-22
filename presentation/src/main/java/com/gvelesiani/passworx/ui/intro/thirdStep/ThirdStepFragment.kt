package com.gvelesiani.passworx.ui.intro.thirdStep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gvelesiani.base.BaseFragment
import com.gvelesiani.passworx.databinding.FragmentThirdStepBinding
import com.gvelesiani.passworx.ui.masterPassword.MasterPasswordAVM
import org.koin.androidx.viewmodel.ext.android.viewModel

class ThirdStepFragment :
    BaseFragment<FragmentThirdStepBinding>() {
    val viewModel: ThirdStepVM by viewModel()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentThirdStepBinding
        get() = FragmentThirdStepBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        binding.svUnlockWithBiometrics.isChecked = false
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            svTakeScreenshots.setOnCheckedChangeListener { _, prevent ->
                viewModel.allowTakingScreenshots(prevent)
            }
            svUnlockWithBiometrics.setOnCheckedChangeListener { _, allow ->
                viewModel.allowBiometrics(allow)
            }
        }
    }

    override fun setupObservers() {}
}