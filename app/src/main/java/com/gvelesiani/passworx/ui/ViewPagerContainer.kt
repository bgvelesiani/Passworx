package com.gvelesiani.passworx.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.adapters.PagerAdapter
import com.gvelesiani.passworx.databinding.FragmentPagerContainerBinding

class ViewPagerContainer : Fragment() {
    private lateinit var binding: FragmentPagerContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerContainerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomBarWithViewPager()
    }

    private fun setupBottomBarWithViewPager() {
        with(binding) {
            viewPager.adapter = PagerAdapter(requireActivity())

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> bottomBar.menu.findItem(R.id.navigation_passwords).isChecked =
                            true
                        1 -> bottomBar.menu.findItem(R.id.navigation_browse).isChecked =
                            true
                        2 -> bottomBar.menu.findItem(R.id.navigation_generate).isChecked =
                            true
                        else -> bottomBar.menu.findItem(R.id.navigation_settings).isChecked =
                            true
                    }
                }
            })

            bottomBar.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_passwords -> {
                        viewPager.currentItem = 0
                        true
                    }
                    R.id.navigation_browse -> {
                        viewPager.currentItem = 1
                        true
                    }
                    R.id.navigation_generate -> {
                        viewPager.currentItem = 2
                        true
                    }
                    else -> {
                        viewPager.currentItem = 3
                        true
                    }
                }
            }
        }
    }
}