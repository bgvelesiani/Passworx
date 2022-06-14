package com.gvelesiani.passworx

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.color.MaterialColors
import com.gvelesiani.passworx.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainVM by viewModel()
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setWindowFlags()
        setContentView(binding.root)
        setupActionBarWithNavController()

        viewModel.getMasterPassword()
        setupObservers()
        onDestinationChanged()

        setBackgroundToActionBar()

//        setupBottomMenu()
    }

    private fun setupObservers() {
        viewModel.viewState.observe(this) {
            val graph = navController.navInflater.inflate(R.navigation.mobile_navigation)
            if (it.masterPassword == "") {
                graph.setStartDestination(R.id.viewPagerContainer)
                navController.popBackStack()
            }
            navController.graph = graph
        }
    }

    private fun setBackgroundToActionBar() {
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                MaterialColors.getColor(
                    binding.root,
                    R.attr.bg_color
                )
            )
        )
    }

    private fun setWindowFlags() {
        /**
         * With FLAG_SECURE, Users will be prevented from taking screenshots of the application,
         * */
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    private fun setupActionBarWithNavController() {
        val appBarConfiguration = AppBarConfiguration
            .Builder(R.id.masterPasswordFragment, R.id.viewPagerContainer)
            .build()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun onDestinationChanged() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.masterPasswordFragment, R.id.viewPagerContainer -> {
                    supportActionBar?.elevation = 0F
                }
                else -> {
                    supportActionBar?.elevation = 8F
                }
            }
        }
    }

//    private fun setupBottomMenu() {
//        val popupMenu = PopupMenu(this, null)
//        popupMenu.inflate(R.menu.bottom_nav_menu)
//        binding.bottomBar.setupWithNavController(navController)
//    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}