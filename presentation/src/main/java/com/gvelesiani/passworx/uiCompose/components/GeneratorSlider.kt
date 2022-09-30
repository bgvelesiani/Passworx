package com.gvelesiani.passworx.uiCompose.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.uiCompose.composeTheme.*

@Composable
fun GeneratorSlider(modifier: Modifier, selectedLength: Float, onValueChange: (Float) -> Unit){
    val inactiveColor = if(isSystemInDarkTheme()) inactiveTrackColorDark else inactiveTrackColorLight

    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier){
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "8",
            textAlign = TextAlign.Center,
            color = if (isSystemInDarkTheme()) textColorDark else textColorLight,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.medium, FontWeight.Normal))
        )

        Slider(
            modifier = Modifier.weight(1f).padding(start = 5.dp, end = 5.dp),
            value = selectedLength,
            onValueChange = {
                onValueChange.invoke(it)
            },
            valueRange = 8f..50f,
            steps = 41,
            colors = SliderDefaults.colors(
                thumbColor = accentColor,
                activeTrackColor = accentColor,
                activeTickColor = accentColor,
                inactiveTrackColor = inactiveColor,
                inactiveTickColor = inactiveColor
            )
        )

        Text(
            text = "50",
            textAlign = TextAlign.Center,
            color = if (isSystemInDarkTheme()) textColorDark else textColorLight,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.medium, FontWeight.Normal))
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}