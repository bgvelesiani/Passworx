package com.gvelesiani.passmanager.diModules

import android.app.Application
import androidx.room.Room
import com.gvelesiani.passmanager.constants.DATABASE_NAME
import com.gvelesiani.passmanager.data.database.LocalDataProvider
import com.gvelesiani.passmanager.data.database.LocalDataProviderImpl
import com.gvelesiani.passmanager.data.database.dao.PasswordDao
import com.gvelesiani.passmanager.data.database.database.PasswordDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module

val localStorageModule = module {
    fun provideDataBase(application: Application): PasswordDatabase {
        return Room.databaseBuilder(application, PasswordDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration() // TODO: 2/26/2022 Remove this and add manual migrations later
            .build()
    }

    fun provideDao(dataBase: PasswordDatabase): PasswordDao {
        return dataBase.getPasswordDao
    }
    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }

    single {
        LocalDataProviderImpl(
            get(),
        )
    } bind LocalDataProvider::class
}