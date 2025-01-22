package mortum.skufservices.exceptionHandlers

import mortum.skufservices.dto.UsernameAlreadyExistsResponse
import mortum.skufservices.exceptions.UsernameAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class SignUpExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    fun handleUsernameAlreadyExistsException(e: UsernameAlreadyExistsException): ResponseEntity<UsernameAlreadyExistsResponse> {
        return ResponseEntity(
            UsernameAlreadyExistsResponse("Пользователь с таким именем пользователя уже существует"),
            HttpStatus.CONFLICT
        )
    }
}