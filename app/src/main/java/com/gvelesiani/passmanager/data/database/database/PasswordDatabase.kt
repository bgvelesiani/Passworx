package com.gvelesiani.passmanager.data.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gvelesiani.passmanager.data.database.dao.PasswordDao
import com.gvelesiani.passmanager.data.models.PasswordModel

@Database(entities = [PasswordModel::class], version = 4, exportSchema = false)
abstract class PasswordDatabase : RoomDatabase() {
    abstract val getPasswordDao: PasswordDao
}