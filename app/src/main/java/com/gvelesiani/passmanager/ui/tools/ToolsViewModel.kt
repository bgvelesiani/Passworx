package com.gvelesiani.passmanager.ui.tools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gvelesiani.passmanager.base.BaseViewModel

class ToolsViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}