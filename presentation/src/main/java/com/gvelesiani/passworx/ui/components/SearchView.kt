package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
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
import com.gvelesiani.passworx.ui.composeTheme.searchViewBgDark
import com.gvelesiani.passworx.ui.composeTheme.searchViewBgLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val maxSearchQueryLength = 20
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            singleLine = true,
            maxLines = 1,
            shape = CircleShape,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = if (isSystemInDarkTheme()) searchViewBgDark else searchViewBgLight,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
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
        androidx.compose.material3.FloatingActionButton(
            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
            onClick = {}) {
            Icon(
                painterResource(
                id = R.drawable.ic_search),
                contentDescription = ""
                //tint = Color.White
            )
        }
    }
}