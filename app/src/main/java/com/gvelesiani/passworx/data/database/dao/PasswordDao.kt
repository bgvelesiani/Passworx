package com.gvelesiani.passworx.data.database.dao

import androidx.room.*
import com.gvelesiani.passworx.constants.TABLE_NAME
import com.gvelesiani.passworx.data.models.PasswordModel

@Dao
interface PasswordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewPassword(pass: PasswordModel)

    @Update
    fun updatePassword(pass: PasswordModel)

    @Query("DELETE FROM $TABLE_NAME WHERE passwordId=:passwordId")
    fun deletePassword(passwordId: Int)

    @Query("SELECT COUNT(passwordId) FROM $TABLE_NAME")
    fun getListSize(): Int

    @Query("UPDATE $TABLE_NAME SET isFavorite=:isFavorite WHERE passwordId=:id")
    fun updateFavoriteState(isFavorite: Boolean, id: Int)

    @Query("UPDATE $TABLE_NAME SET isInTrash=:isInTrash WHERE passwordId=:id")
    fun updateItemTrashState(isInTrash: Boolean, id: Int)

    @Query("SELECT * FROM $TABLE_NAME WHERE isInTrash=:isInTrash")
    fun getPasswords(isInTrash: Boolean): List<PasswordModel>

    @Query("SELECT * FROM $TABLE_NAME WHERE isFavorite=:isFavorite AND isInTrash=0")
    fun getFavorites(isFavorite: Boolean): List<PasswordModel>

    @Query("SELECT * FROM $TABLE_NAME WHERE websiteOrAppName LIKE '%' || :query || '%' AND isInTrash=0")
    fun searchPasswords(query: String): List<PasswordModel>
}