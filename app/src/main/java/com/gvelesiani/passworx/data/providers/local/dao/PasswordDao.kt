package com.gvelesiani.passworx.data.providers.local.dao

import androidx.room.*
import com.gvelesiani.passworx.constants.TABLE_NAME
import com.gvelesiani.passworx.data.dto.PasswordDto

@Dao
interface PasswordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewPassword(pass: PasswordDto)

    @Update
    fun updatePassword(pass: PasswordDto)

    @Query("DELETE FROM $TABLE_NAME WHERE passwordId=:passwordId")
    fun deletePassword(passwordId: Int)

    @Query("SELECT COUNT(passwordId) FROM $TABLE_NAME")
    fun getListSize(): Int

    @Query("UPDATE $TABLE_NAME SET isFavorite=:isFavorite WHERE passwordId=:id")
    fun updateFavoriteState(isFavorite: Boolean, id: Int)

    @Query("UPDATE $TABLE_NAME SET isInTrash=:isInTrash WHERE passwordId=:id")
    fun updateItemTrashState(isInTrash: Boolean, id: Int)

    @Query("SELECT * FROM $TABLE_NAME WHERE isInTrash=:isInTrash")
    fun getPasswords(isInTrash: Boolean): List<PasswordDto>

    @Query("SELECT * FROM $TABLE_NAME WHERE isFavorite=1 AND isInTrash=0")
    fun getFavorites(): List<PasswordDto>

    @Query("SELECT * FROM $TABLE_NAME WHERE websiteOrAppName LIKE '%' || :query || '%' AND isInTrash=:isInTrash")
    fun searchPasswords(query: String, isInTrash: Boolean): List<PasswordDto>
}