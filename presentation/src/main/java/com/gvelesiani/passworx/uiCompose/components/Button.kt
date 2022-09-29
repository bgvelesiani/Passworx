package com.gvelesiani.passworx.uiCompose.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.uiCompose.composeTheme.accentColor
import com.gvelesiani.passworx.uiCompose.composeTheme.accentTransparentDark
import com.gvelesiani.passworx.uiCompose.composeTheme.accentTransparentLight

@Composable
fun GeneralButton(
    enabled: Boolean = false,
    modifier: Modifier,
    backgroundColor: Color = accentColor,
    textColor: Color = Color.White,
    text: String,
    onClick: () -> Unit
) {
    val disabledButtonBg = if(isSystemInDarkTheme()) accentTransparentDark else accentTransparentLight
    Button(
        enabled = enabled,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = disabledButtonBg,
            backgroundColor = backgroundColor
        ),
        onClick = {
            onClick.invoke()
        }) {
        Text(
            modifier = Modifier.padding(top = 7.dp, bottom = 7.dp),
            text = text,
            color = textColor,
            fontFamily = FontFamily(Font(R.font.medium))
        )
    }
}