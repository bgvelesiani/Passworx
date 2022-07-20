package com.gvelesiani.passworx.adapters

import androidx.recyclerview.widget.DiffUtil
import com.gvelesiani.passworx.domain.model.PasswordModel

class PassworxDiffUtil() : DiffUtil.ItemCallback<PasswordModel>() {
    override fun areItemsTheSame(oldItem: PasswordModel, newItem: PasswordModel): Boolean {
        return oldItem.passwordId == newItem.passwordId
    }

    override fun areContentsTheSame(oldItem: PasswordModel, newItem: PasswordModel): Boolean {
        return oldItem == newItem
    }
}