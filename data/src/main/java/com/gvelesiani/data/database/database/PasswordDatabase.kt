package com.gvelesiani.data.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gvelesiani.data.database.dao.PasswordDao
import com.gvelesiani.common.models.data.PasswordDto

@Database(entities = [PasswordDto::class], version = 5, exportSchema = false)
abstract class PasswordDatabase : RoomDatabase() {
    abstract val getPasswordDao: PasswordDao
}