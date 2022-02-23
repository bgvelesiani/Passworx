package com.gvelesiani.passmanager.ui.passwords.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gvelesiani.passmanager.data.models.PasswordModel
import com.gvelesiani.passmanager.databinding.PasswordItemBinding
import java.util.*

class PasswordAdapter(private val clickListener: (PasswordModel) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var passwordList: List<PasswordModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<PasswordModel>) {
        passwordList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PasswordItemBinding.inflate(inflater, parent, false)
        return PasswordViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = passwordList[position]
        (viewHolder as PasswordViewHolder).bind(item, clickListener)
    }

    inner class PasswordViewHolder(private val binding: PasswordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(password: PasswordModel, clickListener: (PasswordModel) -> Unit) {
            binding.tvEmailOrUsername.text = password.emailOrUserName
            binding.tvItemLogo.text = password.websiteOrAppName.subSequence(0, 2).toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            binding.tvPasswordItemName.text = password.websiteOrAppName
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            binding.root.setOnClickListener { clickListener(password) }
        }
    }
    override fun getItemCount() = passwordList.size
}
