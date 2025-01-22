package mortum.skufservices.dto

import mortum.skufservices.persistence.model.order.OrderStatus

data class SetStatusResponse(
    var status: OrderStatus,
)
