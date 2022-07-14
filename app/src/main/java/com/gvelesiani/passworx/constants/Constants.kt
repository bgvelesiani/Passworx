package com.gvelesiani.passworx.constants

const val DATABASE_NAME = "PASSWORD_DATABASE"
const val TABLE_NAME = "PASSWORD_TABLE"
const val INITIAL_GENERATED_PASSWORD_LENGTH = 8
const val CLIP_DATA_PLAIN_TEXT_LABEL = "GENERATED_PASSWORD"
const val MAX_TITLE_LENGTH = 20
const val PREFERENCES_KEY = "com.gvelesiani.passworx_preferences"

object PasswordGeneratorProperties {
    const val CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    const val LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz"
    const val SYMBOLS = "!@#\$%^&*()_?><,.;':}{|][/"
    const val NUMBERS = "0123456789"
    const val COMPLEX_PASSWORD = CAPITAL_LETTERS + LOWERCASE_LETTERS + NUMBERS + SYMBOLS
}

const val MASTER_PASSWORD = "MASTER_PASSWORD"
const val TAKING_SCREENSHOTS_ALLOWED = "TAKING_SCREENSHOTS_ALLOWED"
const val IS_INTRO_FINISHED = "IS_INTRO_FINISHED"

object Steps {
    const val FIRST = 0
    const val SECOND = 1
    const val THIRD = 2
}