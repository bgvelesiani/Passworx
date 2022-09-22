package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.ui.composeTheme.*

@Composable
fun SearchView(onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val maxSearchQueryLength = 20
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            singleLine = true,
            maxLines = 1,
            shape = CircleShape,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = if (isSystemInDarkTheme()) searchViewBgDark else searchViewBgLight,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = accentColor,
                textColor = if(isSystemInDarkTheme()) textColorDark else textColorLight,
                placeholderColor = secondaryTextColor
            ),
            value = text,
            onValueChange = {
                if(it.text.length <= maxSearchQueryLength) text = it
                onValueChange.invoke(it.text)
            },
            placeholder = {
                Text(
                    fontFamily = FontFamily(Font(R.font.regular)),
                    fontSize = 16.sp,
                    text = "Search Passwords"
                )
            })
        FloatingActionButton(
            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
            backgroundColor = accentColor,
            onClick = {}) {
            Icon(
                painterResource(
                id = R.drawable.ic_search),
                contentDescription = "",
                tint = Color.White)
        }
    }
}