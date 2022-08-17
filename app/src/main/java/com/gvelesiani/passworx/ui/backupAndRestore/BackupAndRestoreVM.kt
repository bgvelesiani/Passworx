package com.gvelesiani.passworx.ui.backupAndRestore

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.passworx.constants.DATABASE_NAME
import com.gvelesiani.passworx.data.providers.local.database.PasswordDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class BackupAndRestoreVM : ViewModel(){
    fun export(uri: Uri, context: Context) {
        viewModelScope.launch {
            Mutex(false).withLock {
//                PasswordDatabase.close()

                context.contentResolver.openOutputStream(uri)?.use { stream ->
                    context.getDatabasePath(DATABASE_NAME).inputStream().copyTo(stream)
                }
            }
        }
    }

    fun import(uri: Uri, context: Context) {
        viewModelScope.launch {
            Mutex(false).withLock {
//                provider.close()

                context.contentResolver.openInputStream(uri)?.use { stream ->
                    val dbFile = context.getDatabasePath(DATABASE_NAME)
                    dbFile?.delete()
                    stream.copyTo(dbFile.outputStream())
                }
            }
        }
    }
}