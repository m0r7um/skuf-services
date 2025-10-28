package mortum.skufservices.controller

import mortum.skufservices.dto.PaymentRequest
import mortum.skufservices.service.PaymentService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bank-payment")
class PaymentController(
    private val paymentService: PaymentService,
) {

    @PostMapping
    fun payment(@RequestBody request: PaymentRequest) {
        paymentService.processPayment(request)
    }
}