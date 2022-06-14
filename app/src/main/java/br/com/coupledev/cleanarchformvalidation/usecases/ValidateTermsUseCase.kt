package br.com.coupledev.cleanarchformvalidation.usecases

class ValidateTermsUseCase {

    fun execute(acceptedTerms: Boolean): ValidationOutput {
        if (!acceptedTerms) {
            return ValidationOutput(
                successful = false,
                errorMessage = "Please accept the terms"
            )
        }
        return ValidationOutput(
            successful = true
        )
    }
}