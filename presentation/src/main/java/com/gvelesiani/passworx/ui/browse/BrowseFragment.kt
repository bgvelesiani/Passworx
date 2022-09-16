package com.gvelesiani.passworx.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.findNavController
import com.gvelesiani.base.BaseFragment
import com.gvelesiani.passworx.databinding.FragmentBrowseBinding

@Deprecated("Needs to be deleted soon")
class BrowseFragment :
    BaseFragment<BrowseVM, FragmentBrowseBinding>(BrowseVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBrowseBinding
        get() = FragmentBrowseBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        binding.content.setContent {
            MaterialTheme {
                BrowseScreen(findNavController())
            }
        }
    }

    @Preview
    @Composable
    fun BrowsePreview() {
        BrowseScreen(findNavController())
    }

    override fun setupObservers() {
    }
}