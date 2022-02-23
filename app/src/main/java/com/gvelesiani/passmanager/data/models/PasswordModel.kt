package com.gvelesiani.passmanager.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gvelesiani.passmanager.constants.TABLE_NAME
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = TABLE_NAME)
data class PasswordModel(
    @PrimaryKey(autoGenerate = true)
    val passwordId: Int = 0,
    val password: String = "",
    val passwordTitle: String = "",
    val websiteOrAppName: String = "",
    val emailOrUserName: String = "",
    val label: String = ""
) : Parcelable