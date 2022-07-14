package com.gvelesiani.passworx.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.viewpager2.widget.ViewPager2
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.adapters.IntroAdapter
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.constants.Steps.FIRST
import com.gvelesiani.passworx.constants.Steps.SECOND
import com.gvelesiani.passworx.constants.Steps.THIRD
import com.gvelesiani.passworx.databinding.FragmentIntroBinding
import com.gvelesiani.passworx.ui.masterPassword.fragments.createMasterPassword.CreateMasterPasswordFragment


class IntroFragment : BaseFragment<IntroVM, FragmentIntroBinding>(IntroVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIntroBinding =
        FragmentIntroBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setupViewPagerAdapter()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            btSkipIntro.setOnClickListener { finishIntroAndNavigate() }
            btFinish.setOnClickListener { finishIntroAndNavigate() }
        }
    }

    private fun finishIntroAndNavigate() {
        viewModel.finishIntro()
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<CreateMasterPasswordFragment>(R.id.fContainer)
        }
    }

    private fun setupViewPagerAdapter() {
        binding.introViewPager.adapter = IntroAdapter(requireActivity())
        var currentItem = 0
        binding.introViewPager.currentItem = currentItem

        binding.btNextStep.setOnClickListener {
            if (currentItem != 2)
                currentItem += 1
            binding.introViewPager.currentItem = currentItem
        }

        binding.introViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                with(binding){
                    when (position) {
                        0 -> {
                            btPreviousStep.isVisible = false
                            obView.viewState.step = FIRST
                            btNextStep.isVisible = true
                            btFinish.isVisible = false
                            btSkipIntro.isVisible = true
                            currentItem = FIRST
                        }
                        1 -> {
                            btPreviousStep.isVisible = true
                            obView.viewState.step = SECOND
                            btNextStep.isVisible = true
                            btFinish.isVisible = false
                            btSkipIntro.isVisible = true
                            currentItem = SECOND
                        }
                        else -> {
                            btPreviousStep.isVisible = true
                            obView.viewState.step = THIRD
                            btNextStep.isVisible = false
                            btFinish.isVisible = true
                            btSkipIntro.isVisible = false
                            currentItem = THIRD
                        }
                    }
                }
            }
        })
        binding.btPreviousStep.setOnClickListener {
            currentItem -= 1
            binding.introViewPager.currentItem = currentItem
        }
    }


    override fun setupObservers() {
    }
}