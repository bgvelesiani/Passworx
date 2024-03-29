package com.gvelesiani.common.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gvelesiani.common.constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class PasswordDto(
    @PrimaryKey(autoGenerate = true)
    var passwordId: Int = 0,
    var password: String = "",
    var passwordTitle: String = "",
    var websiteOrAppName: String = "",
    var emailOrUserName: String = "",
    var label: String = "",
    var isFavorite: Boolean = false,
    var isInTrash: Boolean = false
)