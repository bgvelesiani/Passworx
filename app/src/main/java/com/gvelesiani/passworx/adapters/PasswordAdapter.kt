package com.gvelesiani.passworx.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gvelesiani.passworx.databinding.PasswordItemBinding
import com.gvelesiani.passworx.domain.model.PasswordModel
import java.util.*

class PasswordAdapter(
    private val clickListener: (PasswordModel) -> Unit,
    private val menuClickListener: (PasswordModel, View) -> Unit,
    private val copyClickListener: (PasswordModel) -> Unit,
    private val favoriteClickListener: (PasswordModel, Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var passwordList: List<PasswordModel> = arrayListOf()
    var filteredList = ArrayList<PasswordModel>()

    var binding: PasswordItemBinding? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<PasswordModel>) {
        val diffUtil = PassworxDiffUtil(passwordList, data)
        val result = DiffUtil.calculateDiff(diffUtil)
        passwordList = data
        filteredList = data as ArrayList<PasswordModel>
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = PasswordItemBinding.inflate(inflater, parent, false)
        return PasswordViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = filteredList[position]
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
                if (logoResource != 0) {
                    tvItemLogo.setBackgroundResource(logoResource)
                } else {
                    tvItemLogo.text = password.websiteOrAppName.subSequence(0, 2).toString()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                }

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

    override fun getItemCount() = filteredList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint.toString()
                filteredList = if (query.isEmpty()) {
                    passwordList as ArrayList<PasswordModel>
                } else {
                    val resultList = ArrayList<PasswordModel>()
                    for (password in passwordList) {
                        if (password.passwordTitle.lowercase()
                                .contains(constraint.toString().lowercase())
                        ) {
                            resultList.add(password)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<PasswordModel>
                notifyDataSetChanged()
            }
        }
    }
}
