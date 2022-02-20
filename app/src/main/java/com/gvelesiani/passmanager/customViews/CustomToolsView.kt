package com.gvelesiani.passmanager.customViews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.gvelesiani.passmanager.R

class CustomToolsView(context: Context, @Nullable attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var view: View = LayoutInflater.from(context).inflate(R.layout.tools_item, this, true)
    private var imageView: AppCompatImageView
    private var titleTextView: AppCompatTextView
    private var subtitleTextView: AppCompatTextView
    private var imageDrawable: Drawable?
    private var title: String?
    private var subtitle: String?

    init {
        imageView = view.findViewById(R.id.toolImage) as AppCompatImageView
        titleTextView = view.findViewById(R.id.titleTextView) as AppCompatTextView
        subtitleTextView = view.findViewById(R.id.subtitleTextView) as AppCompatTextView

        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomToolsView, 0, 0)

        try {
            imageDrawable = typedArray.getDrawable(R.styleable.CustomToolsView_setImageDrawable)
            title = typedArray.getString(R.styleable.CustomToolsView_setTitle)
            subtitle = typedArray.getString(R.styleable.CustomToolsView_setSubTitle)

            imageView.setImageDrawable(imageDrawable)
            titleTextView.text = title
            subtitleTextView.text = subtitle
        } finally {
            typedArray.recycle()
        }

        fun setImageDrawable(drawable: Drawable?) {
            imageView.setImageDrawable(drawable)
        }

        fun getImageDrawable(): Drawable? {
            return imageDrawable
        }

        fun setTitle(text: String?) {
            titleTextView.text = text
        }

        fun getTitle(): String? {
            return title
        }

        fun setSubtitle(text: String?) {
            subtitleTextView.text = text
        }

        fun getSubtitle(): String? {
            return subtitle
        }
    }
}
