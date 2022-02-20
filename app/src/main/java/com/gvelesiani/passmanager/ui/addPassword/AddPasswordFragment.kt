package com.gvelesiani.passmanager.ui.addPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gvelesiani.passmanager.base.BaseFragment
import com.gvelesiani.passmanager.databinding.FragmentAddPasswordBinding

class AddPasswordFragment : BaseFragment<AddPasswordViewModel, FragmentAddPasswordBinding>(AddPasswordViewModel::class) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddPasswordBinding
        get() = FragmentAddPasswordBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        viewModel.text.observe(viewLifecycleOwner) {
            binding.textNotifications.text = it
        }
    }
}