package br.com.coupledev.cleanarchformvalidation.usecases

data class ValidationOutput(
    val successful: Boolean,
    val errorMessage: String? = null
)
