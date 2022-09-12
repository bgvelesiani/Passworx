package com.gvelesiani.passworx.ui

import android.view.LayoutInflater
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.databinding.ActivityMainBinding

class MainActivity : com.gvelesiani.base.BaseActivity<MainVM, ActivityMainBinding>(MainVM::class) {
    private lateinit var navController: NavController

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun setupView() {
        setupObservers()
        setContentView(binding.root)
        setupNavController()
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupObservers() {
        viewModel.takingScreenshotsArePrevented.observe(this) { prevented ->
            if (prevented == true) {
                /**
                 * With FLAG_SECURE, Users will be prevented from taking screenshots of the application,
                 * */
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            } else {
                window.clearFlags(
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }
        }
    }
}