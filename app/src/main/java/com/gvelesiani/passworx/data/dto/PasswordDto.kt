package com.gvelesiani.passworx.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gvelesiani.passworx.constants.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class PasswordDto(
    @PrimaryKey(autoGenerate = true)
    val passwordId: Int = 0,
    val password: String = "",
    val passwordTitle: String = "",
    val websiteOrAppName: String = "",
    val emailOrUserName: String = "",
    val label: String = "",
    val isFavorite: Boolean = false,
    val isInTrash: Boolean = false
)