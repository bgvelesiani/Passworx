package com.gvelesiani.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseActivity<VM : ViewModel, B : ViewBinding>(klass: KClass<VM>) :
    AppCompatActivity() {

    lateinit var binding: B

    val viewModel: VM by viewModel(clazz = klass)

    abstract val bindingInflater: (LayoutInflater) -> B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(layoutInflater).apply {
            setContentView(root)
        }
        setupView()
    }

    abstract fun setupView()
}