package com.cilestal.mvi_mpp.utils

class DataValidator {
    fun validateEmail(email: String): String? {
        return when {
            email.isEmpty() -> "Empty email error"
            !checkEmail(email) -> "Email should be valid"
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return if (password.length !in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH) {
            return "Password should be in range $MIN_PASSWORD_LENGTH..$MAX_PASSWORD_LENGTH"
        } else {
            null
        }
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 5
        private const val MAX_PASSWORD_LENGTH = 30
    }
}

fun checkEmail(email: String) = Regex(
    "^([_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))|" +
        "([_А-Яа-я0-9-\\+]+(\\.[_А-Яа-я0-9-]+)*@"
        + "[А-Яа-я0-9-]+(\\.[А-Яа-я0-9]+)*(\\.[А-Яа-я]{2,}))$"
)
    .containsMatchIn(email)