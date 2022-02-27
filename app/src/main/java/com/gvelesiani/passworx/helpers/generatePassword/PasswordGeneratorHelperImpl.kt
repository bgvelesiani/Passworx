package com.gvelesiani.passworx.helpers.generatePassword

import java.security.SecureRandom
import java.util.stream.Collectors
import java.util.stream.IntStream

class PasswordGeneratorHelperImpl : PasswordGeneratorHelper {
    override fun generatePassword(length: Int, properties: String): String {
        val random = SecureRandom()
        return IntStream.range(0, length)
            .map { random.nextInt(properties.length) }
            .mapToObj { randomIndex: Int ->
                properties[randomIndex].toString()
            }
            .collect(Collectors.joining())
    }
}