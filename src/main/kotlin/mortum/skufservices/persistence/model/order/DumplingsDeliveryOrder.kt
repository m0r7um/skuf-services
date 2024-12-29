package mortum.skufservices.persistence.model.order

import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import mortum.skufservices.persistence.model.service.Service

@Entity
class DumplingsDeliveryOrder(
    val totalPrice: Double,
    @OneToMany
    @JoinColumn(name = "order_id")
    val orderContent: List<DumplingsDeliveryOrderContent>,
    comment: String,
    rating: Byte,
    status: OrderStatus,
    service: Service,
) : Order(
    comment = comment,
    rating = rating,
    status = status,
    service = service,
)