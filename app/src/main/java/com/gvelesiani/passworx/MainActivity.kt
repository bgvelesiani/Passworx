package com.gvelesiani.passworx

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.color.MaterialColors
import com.gvelesiani.passworx.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setWindowFlags()
        setContentView(binding.root)
        setupActionBarWithNavController()
        onDestinationChanged()

        setBackgroundToActionBar()
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
//        val appBarConfiguration = AppBarConfiguration
//            .Builder(R.id.viewPagerContainer)
//            .build()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun onDestinationChanged() {
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.viewPagerContainer, R.id.changeMasterPasswordFragment,
//                R.id.passwordTrashFragment, R.id.addNewPasswordFragment -> {
//                    supportActionBar?.elevation = 0F
//                }
//                else -> {
//                    supportActionBar?.elevation = 8F
//                }
//            }
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}