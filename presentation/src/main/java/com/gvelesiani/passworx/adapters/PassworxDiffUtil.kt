package com.gvelesiani.passworx.adapters

import androidx.recyclerview.widget.DiffUtil

class PassworxDiffUtil(
    private val oldList: List<com.gvelesiani.common.models.domain.PasswordModel>,
    private val newList: List<com.gvelesiani.common.models.domain.PasswordModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].passwordId == newList[newItemPosition].passwordId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}