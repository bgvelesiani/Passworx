package com.gvelesiani.data.diModules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gvelesiani.data.common.DATABASE_NAME
import com.gvelesiani.data.common.PREFERENCES_KEY
import com.gvelesiani.data.database.dao.PasswordDao
import com.gvelesiani.data.database.database.PasswordDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

object LocalStorageModule {
    fun load() : Module {
        return localStorageModule
    }

    private val localStorageModule = module {
        fun provideDataBase(application: Application): PasswordDatabase {
            return Room.databaseBuilder(application, PasswordDatabase::class.java, DATABASE_NAME)
                .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .fallbackToDestructiveMigration() // TODO: Remove this and add manual migrations later
                .build()
        }

        fun provideDao(dataBase: PasswordDatabase): PasswordDao {
            return dataBase.getPasswordDao
        }


        fun provideSettingsPreferences(app: Application): SharedPreferences =
            app.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)

        single { provideSettingsPreferences(androidApplication()) }

        single { provideDataBase(androidApplication()) }

        single { provideDao(get()) }
    }
}