package br.com.coupledev.cleanarchformvalidation.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.coupledev.cleanarchformvalidation.usecases.ValidateEmailUseCase
import br.com.coupledev.cleanarchformvalidation.usecases.ValidatePasswordUseCase
import br.com.coupledev.cleanarchformvalidation.usecases.ValidateRepeatedPasswordUseCase
import br.com.coupledev.cleanarchformvalidation.usecases.ValidateTermsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val validateEmail: ValidateEmailUseCase = ValidateEmailUseCase(),
    private val validatePassword: ValidatePasswordUseCase = ValidatePasswordUseCase(),
    private val validateRepeatedPassword: ValidateRepeatedPasswordUseCase = ValidateRepeatedPasswordUseCase(),
    private val validateTerms: ValidateTermsUseCase = ValidateTermsUseCase(),
): ViewModel() {

    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.AcceptTerms -> {
                state = state.copy(acceptedTerms = event.isAccepted)
            }
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatedPassword = event.repeatedPassword)
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult = validateRepeatedPassword.execute(state.password, state.repeatedPassword)
        val termsResult = validateTerms.execute(state.acceptedTerms)

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            termsResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = passwordResult.errorMessage,
                termsError = termsResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}
