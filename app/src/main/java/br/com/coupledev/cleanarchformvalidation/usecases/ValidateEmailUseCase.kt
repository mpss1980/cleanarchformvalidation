package br.com.coupledev.cleanarchformvalidation.usecases

import android.util.Patterns

class ValidateEmailUseCase {

    fun execute(email: String): ValidationOutput {
        if (email.isBlank()) {
            return ValidationOutput(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationOutput(
                successful = false,
                errorMessage = "That's not a valid email"
            )
        }
        return ValidationOutput(
            successful = true
        )
    }
}