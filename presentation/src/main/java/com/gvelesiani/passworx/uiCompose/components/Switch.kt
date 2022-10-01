package com.gvelesiani.passworx.uiCompose.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.uiCompose.composeTheme.secondaryTextColor

@Composable
fun Switch(
    shouldBeChecked: Boolean = false,
    text: String,
    description: String = "",
    onCheck: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(shouldBeChecked) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
            )
            androidx.compose.material3.Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    onCheck.invoke(it)
                }
            )
        }
        if (description.isNotEmpty()) {
            Text(
                color = secondaryTextColor,
                text = description,
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
            )
        }
    }
}