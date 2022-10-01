package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R

@Composable
fun BackupAndRestoreInfoCard(text: String, buttonText: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
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
