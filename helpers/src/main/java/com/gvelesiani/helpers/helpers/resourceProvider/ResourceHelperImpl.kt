package com.gvelesiani.helpers.helpers.resourceProvider

import android.content.Context

class ResourceHelperImpl(private val context: Context) : ResourceHelper {
    override fun getString(stringResId: Int): String {
        return context.getString(stringResId)
    }
}