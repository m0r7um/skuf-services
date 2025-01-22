package mortum.skufservices.dto

import mortum.skufservices.persistence.model.order.OrderStatus

data class SetStatusRequest(
    val status: OrderStatus,
)