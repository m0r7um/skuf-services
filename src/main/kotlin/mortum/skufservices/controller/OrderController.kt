package mortum.skufservices.controller

import mortum.skufservices.dto.order.AddOrderRequest
import mortum.skufservices.dto.order.AddOrderResponse
import mortum.skufservices.service.OrderService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(
    val orderService: OrderService,
) {

    @PostMapping
    fun addOrder(@RequestBody addOrderRequest: AddOrderRequest): AddOrderResponse {
        orderService.addOrder(addOrderRequest)
        return AddOrderResponse()
    }
}