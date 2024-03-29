package com.gvelesiani.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gvelesiani.common.constants.TABLE_NAME
import com.gvelesiani.common.models.data.PasswordDto
import kotlinx.coroutines.flow.Flow


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
    fun getPasswords(isInTrash: Boolean): Flow<List<PasswordDto>>

    @Query("SELECT * FROM $TABLE_NAME WHERE isFavorite=1 AND isInTrash=0")
    fun getFavorites(): Flow<List<PasswordDto>>

    @Query("SELECT * FROM $TABLE_NAME WHERE passwordTitle LIKE '%' || :query || '%' AND isInTrash=:isInTrash")
    fun searchPasswords(query: String, isInTrash: Boolean): Flow<List<PasswordDto>>

    @Query("SELECT * FROM $TABLE_NAME WHERE isInTrash=:inTrash")
    fun getPasswordsNoFlow(inTrash: Boolean): List<PasswordDto>
}