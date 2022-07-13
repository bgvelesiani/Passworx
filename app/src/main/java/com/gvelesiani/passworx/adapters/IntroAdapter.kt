package com.gvelesiani.passworx.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gvelesiani.passworx.ui.intro.firstStep.FirstStepFragment
import com.gvelesiani.passworx.ui.intro.secondStep.SecondStepFragment
import com.gvelesiani.passworx.ui.intro.thirdStep.ThirdStepFragment

class IntroAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> FirstStepFragment()
        1 -> SecondStepFragment()
        else -> {
            ThirdStepFragment()
        }
    }
}