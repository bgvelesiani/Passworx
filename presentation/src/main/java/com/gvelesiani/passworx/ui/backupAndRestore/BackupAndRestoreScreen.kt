package com.gvelesiani.passworx.ui.backupAndRestore

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.gson.Gson
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.common.extensions.readFileContent
import com.gvelesiani.passworx.common.extensions.writeInFile
import com.gvelesiani.passworx.constants.DATABASE_FILE_NAME
import com.gvelesiani.passworx.constants.FILE_TYPE
import com.gvelesiani.passworx.ui.components.BackupAndRestoreInfoCard
import com.gvelesiani.passworx.ui.components.ToolbarView
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BackupAndRestoreScreen(
    navController: NavController,
    viewModel: BackupAndRestoreVM = getViewModel()
) {
    val uiState = remember { viewModel.uiState }.collectAsState()
    val backupAndRestoreLaunchType = remember { mutableStateOf(BackupAndRestoreLaunchType.NONE) }

    val externalStoragePermission = rememberPermissionState(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val passwordList: MutableList<PasswordModel> =
        mutableListOf()

    val context = LocalContext.current
    val importResult = remember { mutableStateOf<Intent?>(null) }

    val exportDatabase =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument(FILE_TYPE)) {
            it?.writeInFile(
                viewModel.encryptPasswords(Gson().toJson(passwordList)),
                context
            )
        }

    val importDatabase =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.also { uri ->
                importResult.value = uri
            }
        }

    when (externalStoragePermission.status) {
        PermissionStatus.Granted -> {
            when(backupAndRestoreLaunchType.value) {
                BackupAndRestoreLaunchType.BACKUP -> {
                    exportDatabase.launch(DATABASE_FILE_NAME)
                    backupAndRestoreLaunchType.value = BackupAndRestoreLaunchType.NONE
                }

                BackupAndRestoreLaunchType.RESTORE -> {
                    val intentType = FILE_TYPE
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = intentType
                        putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(intentType))
                    }
                    importDatabase.launch(intent)
                    backupAndRestoreLaunchType.value = BackupAndRestoreLaunchType.NONE
                }

                BackupAndRestoreLaunchType.NONE -> {}
            }
        }
        is PermissionStatus.Denied -> {
            Column {
                // Show some text that indicates the need of permissions
            }
        }
    }

    importResult.value?.let {
        viewModel.insertPreviousPasswords(importResult.value?.data?.readFileContent(context) ?: "")
    }

    Column(
        modifier = Modifier
    ) {
        ToolbarView(
            screenTitle = LocalContext.current.getString(R.string.title_backup_and_restore)
        ) {
            navController.navigateUp()
        }

        BackupAndRestoreInfoCard(
            text = LocalContext.current.getString(R.string.backup_passwords_info),
            buttonText = LocalContext.current.getString(R.string.backup_passwords_button_text)
        ) {
            backupAndRestoreLaunchType.value = BackupAndRestoreLaunchType.BACKUP
            externalStoragePermission.launchPermissionRequest()
        }

        BackupAndRestoreInfoCard(
            text = LocalContext.current.getString(R.string.restore_passwords_info),
            buttonText = LocalContext.current.getString(R.string.restore_passwords_button_text)
        ) {
            backupAndRestoreLaunchType.value = BackupAndRestoreLaunchType.RESTORE
            externalStoragePermission.launchPermissionRequest()
        }
    }

    when (val state = uiState.value) {
        is BackupAndRestoreUiState.Success -> {
            for (model in state.passwords) {
                passwordList.add(model)
            }
        }
        is BackupAndRestoreUiState.RestorePasswordsSuccess -> {}
        is BackupAndRestoreUiState.Empty -> {}
    }
}

enum class BackupAndRestoreLaunchType {
    BACKUP, RESTORE, NONE
}
