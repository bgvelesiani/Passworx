package com.gvelesiani.passworx.uiCompose.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.uiCompose.composeTheme.bgSecondaryLight
import com.gvelesiani.passworx.uiCompose.composeTheme.secondaryTextColor

@Composable
fun GeneralDialog(
    openDialog: MutableState<Boolean>,
    title: String,
    text: String,
    onConfirm: () -> Unit
) {
    if (openDialog.value) {
        AlertDialog(
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
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 16.dp),
                ) {
                    GeneralButton(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(end = 4.dp),
                        backgroundColor = bgSecondaryLight,
                        textColor = secondaryTextColor,
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