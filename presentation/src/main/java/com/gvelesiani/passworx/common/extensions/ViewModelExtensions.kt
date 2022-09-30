package com.gvelesiani.passworx.common.extensions

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.koin.android.compat.ScopeCompat.getViewModel
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext

@OptIn(KoinInternalApi::class)
@Composable
inline fun <reified T: ViewModel> getVM(): T {
    return getViewModel(
        clazz = T::class.java,
        owner = LocalViewModelStoreOwner.current!!,
        scope = GlobalContext.get().scopeRegistry.rootScope
    )
}