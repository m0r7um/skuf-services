package mortum.skufservices.exceptionHandlers

import mortum.skufservices.dto.InvalidOrderStatusResponse
import mortum.skufservices.exceptions.InvalidOrderStatusException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class OrderExceptionHandler {

    @ExceptionHandler(InvalidOrderStatusException::class)
    fun handleInvalidOrderStatusException(e: InvalidOrderStatusException): ResponseEntity<InvalidOrderStatusResponse> {
        logger.error(e.message)
        return ResponseEntity(InvalidOrderStatusResponse("Вы уже оплатили этот заказ"), HttpStatus.BAD_REQUEST, )
    }

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(OrderExceptionHandler::class.java)
    }
}