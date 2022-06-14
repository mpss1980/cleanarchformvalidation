package br.com.coupledev.cleanarchformvalidation.usecases

class ValidateRepeatedPasswordUseCase {

    fun execute(password: String, repeatedPassword: String): ValidationOutput {
        if (password != repeatedPassword) {
            return ValidationOutput(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        }
        return ValidationOutput(
            successful = true
        )
    }
}