package mortum.skufservices.controller

import mortum.skufservices.dto.GetOrderResponse
import mortum.skufservices.dto.PageWrapper
import mortum.skufservices.dto.SetStatusRequest
import mortum.skufservices.dto.SetStatusResponse
import mortum.skufservices.dto.order.AddOrderRequest
import mortum.skufservices.dto.order.AddOrderResponse
import mortum.skufservices.persistence.model.order.OrderStatus
import mortum.skufservices.service.OrderService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(
    val orderService: OrderService,
) {

    @GetMapping
    fun all(
        @RequestParam("search") search: String?,
        @RequestParam("status") status: List<OrderStatus>?,
        @RequestParam("page") page: Int,
    ): PageWrapper<GetOrderResponse> {
        return orderService.getAll(page, search, status)
    }

    @GetMapping("provider")
    fun getAllForProvider(
        @RequestParam("search") search: String?,
        @RequestParam("status") status: List<OrderStatus>?,
        @RequestParam("page") page: Int,
    ): PageWrapper<GetOrderResponse> {
        return orderService.getAllForProvider(page, search, status)
    }

    @PostMapping
    @PreAuthorize(value = "hasRole('USER')")
    fun addOrder(@RequestBody addOrderRequest: AddOrderRequest): AddOrderResponse {
        return orderService.addOrder(addOrderRequest)
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: String): GetOrderResponse? {
        return orderService.getById(id)
    }

    @PatchMapping("/setStatus/{id}")
    fun setOrderStatusById(@PathVariable id: String, @RequestBody request: SetStatusRequest): SetStatusResponse {
        return orderService.updateStatusById(id, request)
    }
}