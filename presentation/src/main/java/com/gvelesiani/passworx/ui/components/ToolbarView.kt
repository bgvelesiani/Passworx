package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R

@Composable
fun ToolbarView(
    imageVector: ImageVector = Icons.Filled.ArrowBack,
    isHomeScreen: Boolean = false,
    screenTitle: String,
    onBackClick: () -> Unit
) {
    Row(
        Modifier
            .height(56.dp)
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (!isHomeScreen) {
            IconButton(
                modifier = Modifier
                    .height(56.dp)
                    .width(56.dp),
                onClick = { onBackClick.invoke() }) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = "Back click area"
                )
            }
        } else {
            Spacer(modifier = Modifier.width(56.dp))
        }
        Text(
            text = screenTitle,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 21.sp,
            fontFamily = FontFamily(Font(R.font.medium, FontWeight.Normal))
        )
        Spacer(modifier = Modifier.width(56.dp))
    }
}