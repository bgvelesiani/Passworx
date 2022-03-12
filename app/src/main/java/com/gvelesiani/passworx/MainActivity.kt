package com.gvelesiani.passworx

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.color.MaterialColors
import com.gvelesiani.passworx.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        /**
         * With FLAG_SECURE, Users will be prevented from taking screenshots of the application,
         * Because I don't want passwords to be captured by user or someone else.
         * */
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        setContentView(binding.root)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                MaterialColors.getColor(
                    binding.root,
                    R.attr.bg_color
                )
            )
        )
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        setupBottomMenu()
    }

    private fun setupBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav_menu)
        binding.bottomBar.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}