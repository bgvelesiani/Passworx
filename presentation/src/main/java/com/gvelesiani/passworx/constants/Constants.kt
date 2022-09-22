package com.gvelesiani.passworx.constants

const val INITIAL_GENERATED_PASSWORD_LENGTH = 8
const val CLIP_DATA_PLAIN_TEXT_LABEL = "GENERATED_PASSWORD"
const val MAX_TITLE_LENGTH = 20

object PasswordGeneratorProperties {
    const val CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    const val LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz"
    const val SYMBOLS = "!@#\$%^&*()_?><,.;':}{|][/"
    const val NUMBERS = "0123456789"
    const val COMPLEX_PASSWORD = CAPITAL_LETTERS + LOWERCASE_LETTERS + NUMBERS + SYMBOLS
}

object Steps {
    const val FIRST = 0
    const val SECOND = 1
    const val THIRD = 2
}

const val DATABASE_FILE_NAME = "db.txt"
const val FILE_TYPE = "text/plain"