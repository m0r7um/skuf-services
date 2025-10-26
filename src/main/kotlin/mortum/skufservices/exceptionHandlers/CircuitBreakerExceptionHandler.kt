package mortum.skufservices.exceptionHandlers

import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import mortum.skufservices.dto.InvalidOrderStatusResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CircuitBreakerExceptionHandler {

    @ExceptionHandler(CallNotPermittedException::class)
    fun handleCallNotPermittedException(e: CallNotPermittedException): ResponseEntity<InvalidOrderStatusResponse> {
        logger.error(e.message)
        return ResponseEntity(InvalidOrderStatusResponse("Апи Яндекс пока не работает"), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(CircuitBreakerExceptionHandler::class.java)
    }
}