package com.gvelesiani.passworx.adapters

import androidx.recyclerview.widget.DiffUtil
import com.gvelesiani.domain.model.PasswordModel

class PassworxDiffUtil(
    private val oldList: List<PasswordModel>,
    private val newList: List<PasswordModel>
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