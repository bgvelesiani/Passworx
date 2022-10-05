package com.gvelesiani.passworx.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gvelesiani.common.models.domain.PasswordModel
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.ui.composeTheme.favoriteIconColor
import com.gvelesiani.passworx.ui.composeTheme.secondaryTextColor
import java.util.Locale


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PasswordItem(
    titleContainerColor: Color = Color(0),
    logoResource: Int = 0,
    password: PasswordModel,
    onPasswordClick: (PasswordModel) -> Unit,
    onFavoriteClick: () -> Unit,
    onCopyClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(0.dp),
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
            if (logoResource == 0) {
                Card(
                    modifier = Modifier.height(38.dp).width(38.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(0.dp),
                    colors = CardDefaults.cardColors(containerColor = titleContainerColor)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = password.passwordTitle.replaceRange(
                                2 until password.passwordTitle.length,
                                ""
                            ).uppercase()
                        )
                    }
                }
            } else {
                Image(
                    painter = painterResource(id = logoResource),
                    contentDescription = "",
                    Modifier.height(38.dp)
                )
            }

            Column(
                Modifier
                    .padding(start = 18.dp)
                    .weight(1f)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
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
            if (!password.isInTrash) {
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
                            id = if (password.isFavorite) R.drawable.ic_favorite else R.drawable.ic_not_favorite,
                        ),
                        "",
                        tint = if (password.isFavorite) favoriteIconColor else secondaryTextColor
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
                        ""
                    )
                }
            }
        }
    }
}