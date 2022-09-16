package com.gvelesiani.passworx.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.ui.composeTheme.*

@Composable
fun OverviewItem(onOverviewItemClick: () -> Unit, image: Int, title: String, subTitle: String) {
    val interactionSource = MutableInteractionSource()
    Row(
        Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onOverviewItemClick.invoke()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(painterResource(image), "Overview Item", tint = if(isSystemInDarkTheme()) colorPrimaryDark else colorPrimaryLight)
        Column(
            Modifier
                .padding(start = 26.dp, end = 16.dp)
                .weight(1f)
        ) {
            Text(
                color = if(isSystemInDarkTheme()) textColorDark else textColorLight,
                text = title,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
            )
            Text(
                color = secondaryTextColor,
                text = subTitle,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
            )
        }
        Icon(
            painterResource(id = R.drawable.ic_overview_item_arrow),
            "Overview Item arrow"
        )
    }
}