package com.gvelesiani.passworx.data.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gvelesiani.passworx.data.database.dao.PasswordDao
import com.gvelesiani.passworx.data.models.PasswordModel

@Database(entities = [PasswordModel::class], version = 5, exportSchema = false)
abstract class PasswordDatabase : RoomDatabase() {
    abstract val getPasswordDao: PasswordDao
}