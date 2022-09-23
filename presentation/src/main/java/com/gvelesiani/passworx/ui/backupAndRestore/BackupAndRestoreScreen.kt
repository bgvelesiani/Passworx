package com.gvelesiani.passworx.ui.backupAndRestore

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.gson.Gson
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.common.extensions.readFileContent
import com.gvelesiani.passworx.common.extensions.writeInFile
import com.gvelesiani.passworx.constants.DATABASE_FILE_NAME
import com.gvelesiani.passworx.constants.FILE_TYPE
import com.gvelesiani.passworx.ui.components.BackupAndRestoreInfoCard
import com.gvelesiani.passworx.ui.components.ToolbarView
import com.gvelesiani.passworx.ui.composeTheme.bgColorDark
import com.gvelesiani.passworx.ui.composeTheme.bgColorLight
import org.koin.androidx.compose.getViewModel

@Composable
fun BackupAndRestoreScreen(
    navController: NavController,
    viewModel: BackupAndRestoreVM = getViewModel()
) {
    val uiState = remember { viewModel.uiState }.collectAsState()

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

    importResult.value?.let {
        viewModel.insertPreviousPasswords(importResult.value?.data?.readFileContent(context) ?: "")
    }

    Column(
        modifier = Modifier
            .background(color = if (isSystemInDarkTheme()) bgColorDark else bgColorLight)
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
            exportDatabase.launch(DATABASE_FILE_NAME)
        }

        BackupAndRestoreInfoCard(
            text = LocalContext.current.getString(R.string.restore_passwords_info),
            buttonText = LocalContext.current.getString(R.string.restore_passwords_button_text)
        ) {
            val intentType = FILE_TYPE
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = intentType
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(intentType))
            }
            importDatabase.launch(intent)
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

