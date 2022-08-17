package com.gvelesiani.passworx.ui.backupAndRestore

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.gson.Gson
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.databinding.FragmentBackupAndRestoreBinding
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.*

class BackupAndRestoreFragment :
    BaseFragment<BackupAndRestoreVM, FragmentBackupAndRestoreBinding>(BackupAndRestoreVM::class) {
    private var passwordList: MutableList<PasswordModel> = mutableListOf()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBackupAndRestoreBinding =
        FragmentBackupAndRestoreBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding){
            btBackupDatabase.setOnClickListener {
                backupDatabase()
            }

            btRestoreDatabase.setOnClickListener {
                restoreDatabase()
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, resultData: Intent?
    ) {
        if (requestCode == 26
            && resultCode == Activity.RESULT_OK
        ) {
            resultData?.data?.also { uri ->
                CoroutineScope(Dispatchers.IO).launch {
                    val insert = async { viewModel.insertPreviousPasswords(readFileContent(uri)) }
                    insert.await().apply { restartApp() }
                }
            }
        } else if (requestCode == 25 && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                writeInFile(uri, Gson().toJson(passwordList))
            }
        }
    }

    override fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            for(model in it.passwords){
                passwordList.add(model)
            }
        }
    }

    private fun backupDatabase() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = FILE_TYPE
        intent.putExtra(Intent.EXTRA_TITLE, DATABASE_FILE_NAME)
        startActivityForResult(intent, 25)
    }

    private fun restoreDatabase() {
        val intentType = FILE_TYPE
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = intentType
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(intentType))
        }

        startActivityForResult(intent, 26)
    }

    private fun readFileContent(uri: Uri?): String {
        val inputStream = uri?.let { requireContext().contentResolver.openInputStream(it) }
        val reader = BufferedReader(
            InputStreamReader(
                inputStream)
        )
        val stringBuilder = StringBuilder()
        stringBuilder.append(reader.readLine())
        inputStream?.close()
        return stringBuilder.toString()
    }

    private fun writeInFile(uri: Uri, text: String) {
        try {
            val outputStream = requireContext().contentResolver.openOutputStream(uri)
            val bw = BufferedWriter(OutputStreamWriter(outputStream))
            bw.write(text)
            bw.flush()
            bw.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun restartApp() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireContext().startActivity(intent)
        if (context is Activity) {
            (context as Activity).finish()
        }
        Runtime.getRuntime().exit(0)
    }

    companion object {
        const val DATABASE_FILE_NAME = "db.txt"
        const val FILE_TYPE = "text/plain"
    }
}