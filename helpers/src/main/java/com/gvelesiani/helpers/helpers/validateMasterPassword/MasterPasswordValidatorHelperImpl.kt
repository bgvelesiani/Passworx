package com.gvelesiani.helpers.helpers.validateMasterPassword

import java.util.regex.Pattern

class MasterPasswordValidatorHelperImpl : MasterPasswordValidatorHelper {
    private val errors: MutableList<MasterPasswordError> = mutableListOf(
        LengthError(),
        NumberError(),
        CapitalLetterError(),
        SmallLetterError(),
        SpecCharError()
    )

    override fun isValidPassword(data: String): Boolean {
        var valid = true
        // Password should be minimum minimum 8 characters long
        if (data.length < 8) {
            valid = false
            errors[0].isValid = false
        } else {
            errors[0].isValid = true
        }
        // Password should contain at least one number
        var exp = ".*[0-9].*"
        var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        var matcher = pattern.matcher(data)
        if (!matcher.matches()) {
            valid = false
            errors[1].isValid = false
        } else {
            errors[1].isValid = true
        }

        // Password should contain at least one capital letter
        exp = ".*[A-Z].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(data)
        if (!matcher.matches()) {
            valid = false
            errors[2].isValid = false
        } else {
            errors[2].isValid = true
        }

        // Password should contain at least one small letter
        exp = ".*[a-z].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(data)
        if (!matcher.matches()) {
            valid = false
            errors[3].isValid = false
        } else {
            errors[3].isValid = true
        }

        // Password should contain at least one special character
        // Allowed special characters : "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
        exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(data)
        if (!matcher.matches()) {
            valid = false
            errors[4].isValid = false
        } else {
            errors[4].isValid = true
        }
        return valid
    }

    override fun getMasterPasswordErrors(): MutableList<MasterPasswordError> = errors

    sealed class MasterPasswordError(open var error: String, open var isValid: Boolean)
    data class NumberError(
        override var error: String = "Number",
        override var isValid: Boolean = false
    ) : MasterPasswordError(error, isValid)

    data class CapitalLetterError(
        override var error: String = "Capital letter",
        override var isValid: Boolean = false
    ) : MasterPasswordError(error, isValid)

    data class SmallLetterError(
        override var error: String = "Lowercase letter",
        override var isValid: Boolean = false
    ) : MasterPasswordError(error, isValid)

    data class LengthError(
        override var error: String = "8 characters",
        override var isValid: Boolean = false
    ) : MasterPasswordError(error, isValid)

    data class SpecCharError(
        override var error: String = "Special character",
        override var isValid: Boolean = false
    ) : MasterPasswordError(error, isValid)

}