package com.gvelesiani.passworx.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.databinding.OverviewItemBinding

class OverviewItemView(context: Context, @Nullable attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var binding = OverviewItemBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        with(binding) {
            val imageView = overviewImage
            val titleTextView = overviewItemTitle
            val subtitleTextView = overviewItemSubTitle

            val typedArray =
                context.theme.obtainStyledAttributes(attrs, R.styleable.OverviewItemView, 0, 0)

            try {
                val imageDrawable =
                    typedArray.getDrawable(R.styleable.OverviewItemView_setImageDrawable)
                val title = typedArray.getString(R.styleable.OverviewItemView_setTitle)
                val subtitle = typedArray.getString(R.styleable.OverviewItemView_setSubTitle)

                imageView.setImageDrawable(imageDrawable)
                titleTextView.text = title
                subtitleTextView.text = subtitle
            } finally {
                typedArray.recycle()
            }
        }
    }
}
