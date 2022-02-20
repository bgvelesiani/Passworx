package com.gvelesiani.passmanager.ui.addPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gvelesiani.passmanager.base.BaseViewModel

class AddPasswordViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}