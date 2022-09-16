package com.gvelesiani.passworx.ui.components

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
import com.gvelesiani.passworx.ui.composeTheme.accentColor

@Composable
fun GeneralButton(modifier: Modifier, text: String,onClick:()->Unit){
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = accentColor
        ),
        onClick = {
            onClick.invoke()
        }) {
        Text(
            modifier = Modifier.padding(top = 7.dp, bottom = 7.dp),
            text = text,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.regular))
        )
    }
}