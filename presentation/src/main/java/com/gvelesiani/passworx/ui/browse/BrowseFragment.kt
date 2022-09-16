package com.gvelesiani.passworx.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.findNavController
import com.gvelesiani.base.BaseFragment
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.databinding.FragmentBrowseBinding
import com.gvelesiani.passworx.ui.composables.OverviewItem
import com.gvelesiani.passworx.ui.composables.ToolbarView

class BrowseFragment :
    BaseFragment<BrowseVM, FragmentBrowseBinding>(BrowseVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBrowseBinding
        get() = FragmentBrowseBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        binding.content.setContent {
            MaterialTheme {
                BrowseContent()
            }
        }
    }

    @Composable
    fun BrowseContent() {
        Column(Modifier.fillMaxSize()) {
            ToolbarView(getString(R.string.title_browse)) {
                findNavController().navigateUp()
            }
            OverviewItem(
                onOverviewItemClick = {
                    findNavController().navigate(R.id.action_navigation_browse_to_passwordFavouritesFragment)
                },
                image = R.drawable.ic_not_favorite,
                title = "Favorites",
                subTitle = "Manage your favorite passwords"
            )
            OverviewItem(
                onOverviewItemClick = {
                    findNavController().navigate(R.id.action_navigation_browse_to_passwordTrashFragment)
                },
                image = R.drawable.ic_passwords,
                title = "Trash",
                subTitle = "Restore or Delete your trashed passwords permanently"
            )
        }
    }

    @Preview
    @Composable
    fun BrowsePreview() {
        BrowseContent()
    }

    override fun setupObservers() {
    }
}