package com.gvelesiani.passworx.diModules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gvelesiani.passworx.constants.DATABASE_NAME
import com.gvelesiani.passworx.constants.PREFERENCES_KEY
import com.gvelesiani.passworx.data.providers.local.LocalDataProvider
import com.gvelesiani.passworx.data.providers.local.LocalDataProviderImpl
import com.gvelesiani.passworx.data.providers.local.dao.PasswordDao
import com.gvelesiani.passworx.data.providers.local.database.PasswordDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module

val localStorageModule = module {
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

    single {
        LocalDataProviderImpl(
            get(), get()
        )
    } bind LocalDataProvider::class
}