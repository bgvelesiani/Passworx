package com.gvelesiani.passworx.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.databinding.FragmentSettingsBinding

class SettingsFragment :
    BaseFragment<SettingsVM, FragmentSettingsBinding>(SettingsVM::class) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingsBinding
        get() = FragmentSettingsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            backClickArea.setOnClickListener {
                findNavController().navigateUp()
            }
            svChangeMasterPassword.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_settings_to_changeMasterPasswordFragment)
            }
            svTakeScreenshots.setOnCheckedChangeListener { _, allow ->
                viewModel.allowTakingScreenshots(!allow)
            }
            svUnlockWithBiometrics.setOnCheckedChangeListener { _, allow ->
                viewModel.allowBiometrics(allow)
            }
        }
    }

    override fun setupObservers() {
        with(viewModel) {
            takingScreenshotsAreAllowed.observe(viewLifecycleOwner) { allowed ->
                binding.svTakeScreenshots.isChecked = !allowed
                if (allowed == true) {
                    requireActivity().window.clearFlags(
                        WindowManager.LayoutParams.FLAG_SECURE
                    )
                } else {
                    /**
                     * With FLAG_SECURE, Users will be prevented from taking screenshots of the application,
                     * */
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_SECURE,
                        WindowManager.LayoutParams.FLAG_SECURE
                    )
                }
            }
            biometricsAreAllowed.observe(viewLifecycleOwner) { allowed ->
                binding.svUnlockWithBiometrics.isChecked = allowed
            }
        }
    }
}