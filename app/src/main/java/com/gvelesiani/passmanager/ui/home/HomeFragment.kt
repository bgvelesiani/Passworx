package com.gvelesiani.passmanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gvelesiani.passmanager.base.BaseFragment
import com.gvelesiani.passmanager.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class
) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
    }
}