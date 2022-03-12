package com.gvelesiani.passworx

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import androidx.activity.OnBackPressedCallback
import java.lang.StringBuilder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


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
        val appBarConfiguration = AppBarConfiguration
            .Builder(R.id.navigation_passwords)
            .build()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController, appBarConfiguration)
        setupBottomMenu()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.masterPasswordFragment -> {
                    supportActionBar?.hide()
                }
                else -> {
                    supportActionBar?.show()
                }
            }
        }
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

//fun md5(s: String): String? {
//    val MD5 = "MD5"
//    try {
//        // Create MD5 Hash
//        val digest: MessageDigest = MessageDigest
//            .getInstance(MD5)
//        digest.update(s.toByteArray())
//        val messageDigest: ByteArray = digest.digest()
//
//        // Create Hex String
//        val hexString = StringBuilder()
//        for (aMessageDigest in messageDigest) {
//            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
//            while (h.length < 2) h = "0$h"
//            hexString.append(h)
//        }
//        return hexString.toString()
//    } catch (e: NoSuchAlgorithmException) {
//        e.printStackTrace()
//    }
//    return ""
//}

object PasswordUtils {
    val random = SecureRandom()

    fun generateSalt(): ByteArray {
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }

    fun isExpectedPassword(password: String, salt: ByteArray, expectedHash: ByteArray): Boolean {
        val pwdHash = hash(password, salt)
        if (pwdHash.size != expectedHash.size) return false
        return pwdHash.indices.all { pwdHash[it] == expectedHash[it] }
    }

    fun hash(password: String, salt: ByteArray): ByteArray {
        val spec = PBEKeySpec(password.toCharArray(), salt, 1000, 256)
        try {
            val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            return skf.generateSecret(spec).encoded
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } catch (e: InvalidKeySpecException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } finally {
            spec.clearPassword()
        }
    }
}


fun main(){
    val pass = "1234567"
    val salt = PasswordUtils.generateSalt()
    val hashed = PasswordUtils.hash(pass, salt)
    print(PasswordUtils.isExpectedPassword(pass, salt, hashed))
}