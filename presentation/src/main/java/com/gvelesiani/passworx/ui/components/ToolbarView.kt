package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.ui.composeTheme.bgColorDark
import com.gvelesiani.passworx.ui.composeTheme.bgColorLight
import com.gvelesiani.passworx.ui.composeTheme.textColorDark
import com.gvelesiani.passworx.ui.composeTheme.textColorLight

@Composable
fun ToolbarView(isHomeScreen: Boolean = false, screenTitle: String, onBackClick: () -> Unit) {
    Row(
        Modifier
            .height(56.dp)
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .background(if (isSystemInDarkTheme()) bgColorDark else bgColorLight),
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
                    imageVector = Icons.Filled.ArrowBack,
                    tint = if (isSystemInDarkTheme()) textColorDark else textColorLight,
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
            color = if (isSystemInDarkTheme()) textColorDark else textColorLight,
            fontSize = 21.sp,
            fontFamily = FontFamily(Font(R.font.medium, FontWeight.Normal))
        )
        Spacer(modifier = Modifier.width(56.dp))
    }
}