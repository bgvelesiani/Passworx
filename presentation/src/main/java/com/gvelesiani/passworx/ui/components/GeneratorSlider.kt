package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GeneratorSlider(modifier: Modifier, selectedLength: Float, onValueChange: (Float) -> Unit) {

    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Spacer(modifier = Modifier.width(16.dp))
        Slider(
            value = selectedLength,
            onValueChange = {
                onValueChange.invoke(it)
            },
            valueRange = 8f..50f,
            steps = 41
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}