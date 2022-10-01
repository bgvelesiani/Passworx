package com.gvelesiani.passworx.uiCompose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R

@Composable
fun ErrorDialog(errorMsg: String) {
    Column {
        val openDialog = remember { mutableStateOf(true) }

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.medium)),
                        fontSize = 20.sp,
                        text = errorMsg
                    )
                },
                confirmButton = {},
                dismissButton = {
                    GeneralButton(modifier = Modifier.fillMaxWidth().padding(15.dp), text = "Dismiss") {
                        openDialog.value = false
                    }
                }
            )
        }
    }
}