package com.gvelesiani.passworx.helpers.resourceProvider

import androidx.annotation.StringRes

interface ResourceHelper {
    fun getString(@StringRes stringResId: Int): String
}