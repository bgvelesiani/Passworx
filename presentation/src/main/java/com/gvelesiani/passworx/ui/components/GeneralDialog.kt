package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R

@Composable
fun GeneralDialog(openDialog: MutableState<Boolean>, msg: String, onConfirm: () -> Unit) {
    Column {
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
                        text = msg
                    )
                },
                confirmButton = {
                    GeneralButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp), "Yes"
                    ) {
                        onConfirm.invoke()
                        openDialog.value = false
                    }
                },
                dismissButton = {
                    GeneralButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp), "No"
                    ) {
                        openDialog.value = false
                    }
                }
            )
        }
    }
}