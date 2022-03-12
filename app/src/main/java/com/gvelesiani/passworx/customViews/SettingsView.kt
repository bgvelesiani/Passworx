package com.gvelesiani.passworx.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.gvelesiani.passworx.R

class SettingsView(context: Context, @Nullable attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var view: View =
        LayoutInflater.from(context).inflate(R.layout.settings_item, this, true)

    init {
        val titleTextView = view.findViewById(R.id.tvSetting) as AppCompatTextView

        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.SettingsView, 0, 0)

        try {
            val title = typedArray.getString(R.styleable.SettingsView_setSettingTitle)
            titleTextView.text = title
        } finally {
            typedArray.recycle()
        }
    }
}
