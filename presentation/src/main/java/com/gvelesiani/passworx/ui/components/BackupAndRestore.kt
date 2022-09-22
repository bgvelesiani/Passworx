package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.ui.composeTheme.bgSecondaryDark
import com.gvelesiani.passworx.ui.composeTheme.bgSecondaryLight
import com.gvelesiani.passworx.ui.composeTheme.textColorDark
import com.gvelesiani.passworx.ui.composeTheme.textColorLight

@Composable
fun BackupAndRestoreInfoCard(text: String, buttonText: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(16.dp),
        backgroundColor = if (isSystemInDarkTheme()) bgSecondaryDark else bgSecondaryLight
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                color = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                text = text,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
            )
            GeneralButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp), text = buttonText
            ) {
                onClick.invoke()
            }
        }
    }
}
