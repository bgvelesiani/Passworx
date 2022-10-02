package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.ui.composeTheme.secondaryTextColor

@Composable
fun GeneralDialog(
    openDialog: MutableState<Boolean>,
    title: String,
    text: String,
    onConfirm: () -> Unit
) {
    if (openDialog.value) {
        androidx.compose.material3.AlertDialog(
            shape = RoundedCornerShape(16.dp),
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontSize = 18.sp,
                    text = title
                )
            },
            text = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = FontFamily(Font(R.font.regular)),
                    fontSize = 16.sp,
                    text = text,
                    color = secondaryTextColor
                )
            },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                ) {
                    GeneralButton(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(end = 4.dp),
                        text = "Cancel"
                    ) {
                        openDialog.value = false
                    }
                    GeneralButton(
                        modifier = Modifier
                            .weight(0.5f, true)
                            .padding(start = 4.dp),
                        text = "Confirm"
                    ) {
                        onConfirm.invoke()
                        openDialog.value = false
                    }
                }
            }
        )
    }
}