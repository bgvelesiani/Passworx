package com.gvelesiani.passworx.common

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class ActionLiveData<T> : MutableLiveData<T>() {
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasObservers()) {
            throw Throwable("Only one observer at a time may subscribe to a ActionLiveData")
        }

        super.observe(owner) { data ->
            if (data != null) {
                observer.onChanged(data)
                value = null
            }
        }
    }

    @MainThread
    fun sendAction(data: T) {
        value = data
    }
}