package com.gvelesiani.passmanager.constants

const val INITIAL_GENERATED_PASSWORD_LENGTH = 8
const val CLIP_DATA_PLAIN_TEXT_LABEL = "GENERATED_PASSWORD"

object PasswordGeneratorProperties {
    const val CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    const val LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz"
    const val SYMBOLS = "!@#\$%^&*()_?><,.;':}{|][/"
    const val NUMBERS = "0123456789"
    const val COMPLEX_PASSWORD = CAPITAL_LETTERS + LOWERCASE_LETTERS + NUMBERS + SYMBOLS
}

enum class PasswordProperties(val passwordProperty: String) {
    CAPITAL_LETTERS("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    LOWERCASE_LETTERS("abcdefghijklmnopqrstuvwxyz"),
    SYMBOLS("!@#\$%^&*()_?><,.;':}{|][/"),
    NUMBERS("0123456789"),
    COMPLEX_PASSWORD("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#\$%^&*()_?><,.;':}{|][/0123456789")
}