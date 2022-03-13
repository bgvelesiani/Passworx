package com.gvelesiani.passworx.data.models

data class MasterPasswordModel(
    val hashedPassword: ByteArray = "".toByteArray(),
    val salt: ByteArray = "".toByteArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MasterPasswordModel

        if (!hashedPassword.contentEquals(other.hashedPassword)) return false
        if (!salt.contentEquals(other.salt)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hashedPassword.contentHashCode()
        result = 31 * result + salt.contentHashCode()
        return result
    }
}