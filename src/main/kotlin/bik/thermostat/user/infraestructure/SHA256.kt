package bik.thermostat.user.infraestructure

import java.util.UUID

class SHA256 {
    companion object {
        private fun generateRandomString(): String {
            return UUID.randomUUID().toString()
        }

        fun generateSalt(): String {
            return generateRandomString()
        }

        fun hash(word: String, salt: String): String {
            return org.apache.commons.codec.digest.DigestUtils.sha256Hex(word + salt)
        }

        fun isValidHash(hash: String, salt: String, pass: String): Boolean {
            val hashed = hash(pass, salt)
            println("Hash  $hashed is equals to $hash?")
            return hashed == hash
        }
    }
}
