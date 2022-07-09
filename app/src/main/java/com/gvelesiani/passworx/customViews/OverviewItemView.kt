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

class OverviewItemView(context: Context, @Nullable attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var view: View =
        LayoutInflater.from(context).inflate(R.layout.overview_item, this, true)

    init {
        val imageView = view.findViewById(R.id.overviewImage) as AppCompatImageView
        val titleTextView = view.findViewById(R.id.overviewItemTitle) as AppCompatTextView
        val subtitleTextView = view.findViewById(R.id.overviewItemSubTitle) as AppCompatTextView

        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.OverviewItemView, 0, 0)

        try {
            val imageDrawable = typedArray.getDrawable(R.styleable.OverviewItemView_setImageDrawable)
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
