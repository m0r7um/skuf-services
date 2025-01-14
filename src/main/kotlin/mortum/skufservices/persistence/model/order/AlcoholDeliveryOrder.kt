package mortum.skufservices.persistence.model.order

import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import mortum.skufservices.persistence.model.service.ServiceModel

@Entity
class AlcoholDeliveryOrder(
    val totalPrice: Double,
    @OneToMany
    @JoinColumn(name = "order_id")
    val content: List<AlcoholDeliveryOrderContent>,
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