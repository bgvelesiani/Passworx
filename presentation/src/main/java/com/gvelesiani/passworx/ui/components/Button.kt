package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.gvelesiani.passworx.R

@Composable
fun GeneralButton(
    enabled: Boolean = true,
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        enabled = enabled,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClick.invoke()
        }) {
        Text(
            modifier = Modifier.padding(top = 7.dp, bottom = 7.dp),
            text = text,
            fontFamily = FontFamily(Font(R.font.medium))
        )
    }
}