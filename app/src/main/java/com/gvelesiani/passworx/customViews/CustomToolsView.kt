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

class CustomToolsView(context: Context, @Nullable attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var view: View = LayoutInflater.from(context).inflate(R.layout.tools_item, this, true)

    init {
        val imageView = view.findViewById(R.id.toolImage) as AppCompatImageView
        val titleTextView = view.findViewById(R.id.toolTitle) as AppCompatTextView
        val subtitleTextView = view.findViewById(R.id.toolSubTitle) as AppCompatTextView

        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomToolsView, 0, 0)

        try {
            val imageDrawable = typedArray.getDrawable(R.styleable.CustomToolsView_setImageDrawable)
            val title = typedArray.getString(R.styleable.CustomToolsView_setTitle)
            val subtitle = typedArray.getString(R.styleable.CustomToolsView_setSubTitle)

            imageView.setImageDrawable(imageDrawable)
            titleTextView.text = title
            subtitleTextView.text = subtitle
        } finally {
            typedArray.recycle()
        }
    }
}
