package com.gvelesiani.passworx.ui

import androidx.lifecycle.ViewModel
import com.gvelesiani.common.models.PassworxColors
import com.gvelesiani.domain.useCases.appColors.GetAppColorsUseCase
import com.gvelesiani.domain.useCases.appColors.SetAppColorsUseCase
import com.gvelesiani.passworx.ui.theme.supportsDynamic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ThemeSharedVM(
    private val getAppColorsUseCase: GetAppColorsUseCase,
    private val setAppColorsUseCase: SetAppColorsUseCase
) : ViewModel() {
    val currentThemeColors: MutableStateFlow<PassworxColors> = MutableStateFlow(PassworxColors.Red)

    init {
        getCurrentAppTheme(supportsDynamic())
    }

    fun getCurrentAppTheme(supportsDynamic: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            val themeColors: PassworxColors = when (getAppColorsUseCase(supportsDynamic)) {
                "Blue" -> PassworxColors.Blue
                "Red" -> PassworxColors.Red
                "Green" -> PassworxColors.Green
                "Dynamic" -> PassworxColors.Dynamic
                else -> {
                    PassworxColors.Red
                }
            }
            currentThemeColors.value = themeColors
        }
    }

    fun setThemeColors(passworxColors: PassworxColors) {
        CoroutineScope(Dispatchers.IO).launch {
            setAppColorsUseCase(passworxColors)
        }
    }
}