package mortum.skufservices.dto

import mortum.skufservices.persistence.model.order.OrderStatus
import java.math.BigDecimal

data class GetOrderResponse(
    val id: String,
    val title: String,
    val description: String,
    val status: OrderStatus,
    val totalPrice: BigDecimal,
    val user: UserResponse,
)