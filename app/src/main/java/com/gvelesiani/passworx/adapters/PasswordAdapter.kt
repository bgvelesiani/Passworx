package com.gvelesiani.passworx.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gvelesiani.domain.model.PasswordModel
import com.gvelesiani.passworx.databinding.PasswordItemBinding
import java.util.*

class PasswordAdapter(
    private val clickListener: (PasswordModel) -> Unit,
    private val menuClickListener: (PasswordModel, View) -> Unit,
    private val copyClickListener: (PasswordModel) -> Unit,
    private val favoriteClickListener: (PasswordModel, Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var passwordList: List<PasswordModel> = arrayListOf()

    var binding: PasswordItemBinding? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<PasswordModel>) {
        val diffUtil = PassworxDiffUtil(passwordList, data)
        val result = DiffUtil.calculateDiff(diffUtil)
        passwordList = data
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = PasswordItemBinding.inflate(inflater, parent, false)
        return PasswordViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = passwordList[position]
        (viewHolder as PasswordViewHolder).bind(
            item,
            clickListener,
            menuClickListener,
            copyClickListener,
            position
        )
    }

    inner class PasswordViewHolder(private val binding: PasswordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            password: PasswordModel,
            clickListener: (PasswordModel) -> Unit,
            menuClickListener: (PasswordModel, View) -> Unit,
            copyClickListener: (PasswordModel) -> Unit,
            position: Int
        ) {
            with(binding) {
                tvEmailOrUsername.text = password.emailOrUserName

                val logoResource = tvItemLogo.context.resources.getIdentifier(
                    password.websiteOrAppName.lowercase().replace("\\s".toRegex(), ""),
                    "drawable",
                    "com.gvelesiani.passworx"
                )
//                if (logoResource != 0) {
//                    tvItemLogo.setBackgroundResource(logoResource)
//                } else {
//                    tvItemLogo.text = password.websiteOrAppName.subSequence(0, 2).toString()
//                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
//                }

                tvPasswordItemName.text = password.passwordTitle
                    .lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                root.setOnClickListener { clickListener(password) }
                copyClickView.setOnClickListener { copyClickListener(password) }
                menuClickView.setOnClickListener {
                    menuClickListener(
                        password,
                        it
                    )
                }
                binding.favoriteClickView.setOnClickListener {
                    favoriteClickListener(
                        password, position
                    )
                }

                if (password.isFavorite) {
                    btAddToFavorites.isVisible = false
                    btRemoveFromFavorites.isVisible = true
                } else {
                    btAddToFavorites.isVisible = true
                    btRemoveFromFavorites.isVisible = false
                }

                btCopyPassword.isVisible = !password.isInTrash
                btAddToFavorites.isVisible = !password.isInTrash
                copyClickView.isVisible = !password.isInTrash
                favoriteClickView.isVisible = !password.isInTrash

            }
        }
    }

    override fun getItemCount() = passwordList.size
}
