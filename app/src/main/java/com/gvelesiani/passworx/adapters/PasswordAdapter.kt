package com.gvelesiani.passworx.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gvelesiani.passworx.data.dto.PasswordDto
import com.gvelesiani.passworx.databinding.PasswordItemBinding
import com.gvelesiani.passworx.domain.model.PasswordModel
import java.util.*

class PasswordAdapter(
    private val clickListener: (PasswordModel) -> Unit,
    private val menuClickListener: (PasswordModel, View, Int) -> Unit,
    private val copyClickListener: (PasswordModel) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var passwordList: List<PasswordModel> = emptyList()

    var binding: PasswordItemBinding? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<PasswordModel>) {
        passwordList = data
        notifyDataSetChanged()
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
            menuClickListener: (PasswordModel, View, Int) -> Unit,
            copyClickListener: (PasswordModel) -> Unit,
            position: Int
        ) {
            binding.tvEmailOrUsername.text = password.emailOrUserName
            binding.tvItemLogo.text = password.websiteOrAppName.subSequence(0, 2).toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            binding.tvPasswordItemName.text = password.websiteOrAppName
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            binding.root.setOnClickListener { clickListener(password) }
            binding.copyClickView.setOnClickListener { copyClickListener(password) }
            binding.menuClickView.setOnClickListener { menuClickListener(password, it, position) }
        }
    }

    override fun getItemCount() = passwordList.size
}
