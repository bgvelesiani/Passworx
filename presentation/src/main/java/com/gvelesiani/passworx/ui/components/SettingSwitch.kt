package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.ui.composeTheme.*

@Composable
fun SettingSwitch(
    shouldBeChecked: Boolean,
    text: String,
    description: String,
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
                .fillMaxWidth()
                .clickable {
                    checked = !checked
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                color = if (isSystemInDarkTheme()) textColorDark else textColorLight,
                text = text,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
            )
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    onCheck.invoke(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = accentColor,
                    checkedTrackColor = if (isSystemInDarkTheme()) accentTransparentDark else accentTransparentLight
                )
            )
        }
        Text(
            color = secondaryTextColor,
            text = description,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
        )
    }
}