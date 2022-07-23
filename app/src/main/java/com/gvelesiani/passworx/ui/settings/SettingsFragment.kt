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
        binding.toolbar.setupToolbar {
            findNavController().navigateUp()
        }
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            svChangeMasterPassword.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_settings_to_changeMasterPasswordFragment)
            }
            svTakeScreenshots.setOnCheckedChangeListener { _, prevent ->
                viewModel.allowTakingScreenshots(prevent)
            }
            svUnlockWithBiometrics.setOnCheckedChangeListener { _, allow ->
                viewModel.allowBiometrics(allow)
            }
        }
    }

    override fun setupObservers() {
        with(viewModel) {
            takingScreenshotsArePrevented.observe(viewLifecycleOwner) { prevented ->
                binding.svTakeScreenshots.isChecked = prevented
                if (prevented == true) {
                    /**
                     * With FLAG_SECURE, Users will be prevented from taking screenshots of the application,
                     * */
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_SECURE,
                        WindowManager.LayoutParams.FLAG_SECURE
                    )
                } else {
                    requireActivity().window.clearFlags(
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