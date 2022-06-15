package com.gvelesiani.passworx.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gvelesiani.passworx.ui.browse.BrowseFragment
import com.gvelesiani.passworx.ui.passwordGenerator.PasswordGeneratorFragment
import com.gvelesiani.passworx.ui.passwords.PasswordsFragment
import com.gvelesiani.passworx.ui.settings.SettingsFragment

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> PasswordsFragment()
        1 -> BrowseFragment()
        2 -> PasswordGeneratorFragment()
        else -> {
            SettingsFragment()
        }
    }
}