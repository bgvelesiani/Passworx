package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.ui.composeTheme.secondaryTextColor
import com.gvelesiani.passworx.ui.composeTheme.textColorDark
import com.gvelesiani.passworx.ui.composeTheme.textColorLight

@Composable
fun EmptyListView() {
    val textColor = if(isSystemInDarkTheme()) textColorDark else textColorLight
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "\uD83E\uDD77\uD83C\uDFFB",
            fontSize = 30.sp
        )

        Text(
            color = textColor,
            modifier = Modifier.padding(top = 15.dp),
            textAlign = TextAlign.Center,
            text = "Nothing yet...",
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(R.font.medium))
        )

        Text(
            color = secondaryTextColor,
            modifier = Modifier.padding(top = 10.dp),
            textAlign = TextAlign.Center,
            text = "Store passwords in Passworx, Make them more secure",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.regular))
        )
    }
}