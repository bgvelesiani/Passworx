package com.gvelesiani.passworx.ui.backupAndRestore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.gvelesiani.domain.model.PasswordModel
import com.gvelesiani.passworx.common.makeSnackBar
import com.gvelesiani.passworx.common.readFileContent
import com.gvelesiani.passworx.common.writeInFile
import com.gvelesiani.passworx.databinding.FragmentBackupAndRestoreBinding

class BackupAndRestoreFragment :
    com.gvelesiani.base.BaseFragment<BackupAndRestoreVM, FragmentBackupAndRestoreBinding>(
        BackupAndRestoreVM::class
    ) {
    private var passwordList: MutableList<PasswordModel> = mutableListOf()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBackupAndRestoreBinding =
        FragmentBackupAndRestoreBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            btBackupDatabase.setOnClickListener {
                exportDatabase.launch(DATABASE_FILE_NAME)
            }

            btRestoreDatabase.setOnClickListener {
                val intentType = FILE_TYPE
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = intentType
                    putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(intentType))
                }
                importDatabase.launch(intent)
            }
            toolbar.setupToolbar {
                findNavController().navigateUp()
            }
        }
    }

    private val exportDatabase =
        registerForActivityResult(ActivityResultContracts.CreateDocument(FILE_TYPE)) {
            it?.writeInFile(
                viewModel.encryptPasswords(Gson().toJson(passwordList)),
                requireContext()
            )
        }

    private val importDatabase =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it?.data?.also { uri ->
                viewModel.insertPreviousPasswords(uri.data?.readFileContent(requireContext()) ?: "")
            }
        }

    override fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            for (model in it.passwords) {
                passwordList.add(model)
            }

            it.restorePasswordsSuccess?.makeSnackBar(binding.root)
        }
    }

    companion object {
        const val DATABASE_FILE_NAME = "db.txt"
        const val FILE_TYPE = "text/plain"
    }
}