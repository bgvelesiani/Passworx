package com.gvelesiani.passworx.common.util

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.gvelesiani.common.models.PassworxColors
import com.gvelesiani.passworx.ui.theme.RedThemeDarkColors
import com.gvelesiani.passworx.ui.theme.RedThemeLightColors
import com.gvelesiani.passworx.ui.theme.blue.BlueThemeDarkColors
import com.gvelesiani.passworx.ui.theme.blue.BlueThemeLightColors
import com.gvelesiani.passworx.ui.theme.green.GreenThemeDarkColors
import com.gvelesiani.passworx.ui.theme.green.GreenThemeLightColors

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun getPrimaryColorByTheme(context: Context, colors: PassworxColors): Color {
    return when (colors) {
        PassworxColors.Dynamic -> {
            if (isSystemInDarkTheme()) dynamicDarkColorScheme(context).primary else dynamicLightColorScheme(
                context
            ).primary
        }

        PassworxColors.Red -> {
            if (isSystemInDarkTheme()) RedThemeDarkColors.primary else RedThemeLightColors.primary
        }
        PassworxColors.Green -> {
            if (isSystemInDarkTheme()) GreenThemeDarkColors.primary else GreenThemeLightColors.primary
        }
        PassworxColors.Blue -> {
            if (isSystemInDarkTheme()) BlueThemeDarkColors.primary else BlueThemeLightColors.primary
        }
    }
}

@Composable
fun getSurfaceVariantColorByTheme(context: Context, colors: PassworxColors): Color {
    return when (colors) {
        PassworxColors.Dynamic -> {
            if (isSystemInDarkTheme()) dynamicDarkColorScheme(context).surfaceVariant else dynamicLightColorScheme(
                context
            ).surfaceVariant
        }

        PassworxColors.Red -> {
            if (isSystemInDarkTheme()) RedThemeDarkColors.surfaceVariant else RedThemeLightColors.surfaceVariant
        }
        PassworxColors.Green -> {
            if (isSystemInDarkTheme()) GreenThemeDarkColors.surfaceVariant else GreenThemeLightColors.surfaceVariant
        }
        PassworxColors.Blue -> {
            if (isSystemInDarkTheme()) BlueThemeDarkColors.surfaceVariant else BlueThemeLightColors.surfaceVariant
        }
    }
}