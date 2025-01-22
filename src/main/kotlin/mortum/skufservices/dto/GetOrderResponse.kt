package mortum.skufservices.dto

import mortum.skufservices.persistence.model.order.OrderStatus
import mortum.skufservices.persistence.model.service.ServiceType
import java.math.BigDecimal

data class GetOrderResponse(
    val id: String,
    val type: ServiceType,
    val title: String,
    val description: String,
    val status: OrderStatus,
    val totalPrice: BigDecimal,
    val user: UserResponse,
    val provider: UserResponse,
    val address: String?,
)