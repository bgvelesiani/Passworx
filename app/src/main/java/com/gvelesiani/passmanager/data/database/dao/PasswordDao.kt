package com.gvelesiani.passmanager.data.database.dao

import androidx.room.*
import com.gvelesiani.passmanager.constants.TABLE_NAME
import com.gvelesiani.passmanager.data.models.PasswordModel

@Dao
interface PasswordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewPassword(pass: PasswordModel)

    @Update
    fun updatePassword(pass: PasswordModel)

    @Delete
    fun deletePassword(todo: PasswordModel)

    @Query("SELECT * FROM $TABLE_NAME")
    fun getPasswords(): List<PasswordModel>

    @Query("SELECT COUNT(passwordId) FROM $TABLE_NAME")
    fun getListSize(): Int
}