package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.ui.composeTheme.*
import java.util.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PasswordItem(
    logoResource: Int = 0,
    password: PasswordModel,
    onPasswordClick: (PasswordModel) -> Unit,
    onFavoriteClick: () -> Unit,
    onCopyClick: () -> Unit
) {
    val textAndIconColor = if (isSystemInDarkTheme()) textColorDark else textColorLight
    Card(
        backgroundColor = if (isSystemInDarkTheme())
            passwordBgColorDark
        else
            passwordBgColorLight,
        shape = RoundedCornerShape(20.dp),
        elevation = 0.dp,
        modifier = Modifier
            .padding(start = 16.dp, bottom = 15.dp, end = 16.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
            ) {
                onPasswordClick.invoke(password)
            }
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(15.dp))
            Image(
                painter = painterResource(id = if (logoResource != 0) logoResource else R.drawable.facebook),
                contentDescription = "",
                Modifier.height(38.dp)
            )

            Column(
                Modifier
                    .padding(start = 18.dp)
                    .weight(1f)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    color = textAndIconColor,
                    text = password.passwordTitle.lowercase().replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    },
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.medium, FontWeight.Normal))
                )
                Text(
                    color = secondaryTextColor,
                    text = password.emailOrUserName,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            Column(
                Modifier
                    .width(40.dp)
                    .align(Alignment.CenterVertically)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        onFavoriteClick.invoke()
                    }
            ) {
                Icon(
                    painterResource(
                        id = if (password.isFavorite) R.drawable.ic_favorite else R.drawable.ic_not_favorite
                    ),
                    "",
                    tint = if (password.isFavorite) favoriteIconColor else textAndIconColor
                )
            }
            Column(
                Modifier
                    .width(40.dp)
                    .align(Alignment.CenterVertically)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        onCopyClick.invoke()
                    }
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_copy_password),
                    "",
                    tint = textAndIconColor
                )
            }
        }
    }
}