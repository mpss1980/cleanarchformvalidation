package br.com.coupledev.cleanarchformvalidation.usecases

class ValidatePasswordUseCase {

    fun execute(password: String): ValidationOutput {
        if (password.length < 8) {
            return ValidationOutput(
                successful = false,
                errorMessage = "The password need to consist of at least 8 characters"
            )
        }

        val containsLettersAndDigits = password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return ValidationOutput(
                successful = false,
                errorMessage = "The passrowd needs to contain at least one letter and digit"
            )
        }
        return ValidationOutput(
            successful = true
        )
    }
}