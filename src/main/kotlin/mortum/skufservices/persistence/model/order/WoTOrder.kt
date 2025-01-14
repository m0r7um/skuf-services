package mortum.skufservices.persistence.model.order

import jakarta.persistence.Entity
import mortum.skufservices.persistence.model.service.ServiceModel

@Entity
class WoTOrder(
    comment: String,
    rating: Byte,
    status: OrderStatus,
    service: ServiceModel,
) : Order(
    comment = comment,
    rating = rating,
    status = status,
    service = service,
)