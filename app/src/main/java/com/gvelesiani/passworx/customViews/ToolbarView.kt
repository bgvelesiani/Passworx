package com.gvelesiani.passworx.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.Nullable
import com.google.android.material.appbar.AppBarLayout
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.databinding.ToolbarViewBinding

class ToolbarView(context: Context, @Nullable attrs: AttributeSet) :
    AppBarLayout(context, attrs) {

    private var binding = ToolbarViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        with(binding) {
            val toolbarImage = ivToolbar
            val toolbarTitle = tvToolbarTitle

            val typedArray =
                context.theme.obtainStyledAttributes(attrs, R.styleable.ToolbarView, 0, 0)

            try {
                val image =
                    typedArray.getDrawable(R.styleable.ToolbarView_setToolbarImage)
                val title = typedArray.getString(R.styleable.ToolbarView_setToolbarTitle)

                toolbarImage.setImageDrawable(image)
                toolbarTitle.text = title
            } finally {
                typedArray.recycle()
            }
        }
    }

    fun setupToolbar(backClick: () -> Unit) {
        binding.ivToolbar.setOnClickListener {
            backClick()
        }
    }
}