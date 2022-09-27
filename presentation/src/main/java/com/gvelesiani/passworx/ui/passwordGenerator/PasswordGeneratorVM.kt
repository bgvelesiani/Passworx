package com.gvelesiani.passworx.ui.passwordGenerator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvelesiani.domain.useCases.generate.GeneratePasswordUseCase
import com.gvelesiani.domain.useCases.generate.Password
import com.gvelesiani.helpers.helpers.resourceProvider.ResourceHelper
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.constants.INITIAL_GENERATED_PASSWORD_LENGTH
import com.gvelesiani.passworx.constants.PasswordGeneratorProperties
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PasswordGeneratorVM(
    private val generatePasswordUseCase: GeneratePasswordUseCase,
    private val resourceHelper: ResourceHelper
) : ViewModel() {
    private var _generatePasswordError: MutableLiveData<String> = MutableLiveData()
    val generatePasswordError: LiveData<String> = _generatePasswordError

    private var _passwordProperties: MutableLiveData<String> = MutableLiveData()

    private var _generatedPassword: MutableStateFlow<String> = MutableStateFlow("")
    val generatedPassword: MutableStateFlow<String> = _generatedPassword


    init {
        _passwordProperties.value = PasswordGeneratorProperties.COMPLEX_PASSWORD
        generatePassword(INITIAL_GENERATED_PASSWORD_LENGTH)
    }

    private val _selectedLength = MutableStateFlow(8f)
    val selectedLength: MutableStateFlow<Float> = _selectedLength

    fun onValueChanged(selected: Float) {
        _selectedLength.value = selected
    }

    fun generatePassword(length: Int) {
        viewModelScope.launch {
            try {
                val result = generatePasswordUseCase.invoke(
                    Password(
                        length,
                        _passwordProperties.value!!
                    )
                )
                _generatedPassword.value = result
            } catch (e: Exception) {
                _generatePasswordError.value =
                    resourceHelper.getString(R.string.generate_password_error_Text)
            }
        }
    }

    fun useCapitalLetters(b: Boolean, len: Int) {
        if (b) {
            _passwordProperties.value =
                _passwordProperties.value + PasswordGeneratorProperties.CAPITAL_LETTERS
        } else {
            _passwordProperties.value =
                _passwordProperties.value?.replace(PasswordGeneratorProperties.CAPITAL_LETTERS, "")
        }
        generatePassword(len)
    }

    fun useSymbols(b: Boolean, len: Int) {
        if (b) {
            _passwordProperties.value =
                _passwordProperties.value + PasswordGeneratorProperties.SYMBOLS
        } else {
            _passwordProperties.value =
                _passwordProperties.value?.replace(PasswordGeneratorProperties.SYMBOLS, "")
        }
        generatePassword(len)
    }

    fun useNumbers(b: Boolean, len: Int) {
        if (b) {
            _passwordProperties.value =
                _passwordProperties.value + PasswordGeneratorProperties.NUMBERS
        } else {
            _passwordProperties.value =
                _passwordProperties.value?.replace(PasswordGeneratorProperties.NUMBERS, "")
        }
        generatePassword(len)
    }
}