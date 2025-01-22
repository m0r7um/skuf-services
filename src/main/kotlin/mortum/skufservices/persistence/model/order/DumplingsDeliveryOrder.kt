package mortum.skufservices.persistence.model.order

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import mortum.skufservices.persistence.model.service.ServiceModel
import mortum.skufservices.persistence.model.user.User
import java.math.BigDecimal

@Entity
@DiscriminatorValue(value = "DUMPLINGS")
class DumplingsDeliveryOrder(
    totalPrice: BigDecimal,
    @OneToMany
    @JoinColumn(name = "order_id")
    val content: List<DumplingsDeliveryOrderContent>?,
    comment: String?,
    rating: Byte?,
    status: OrderStatus,
    service: ServiceModel,
    user: User,
    address: String,
) : Order(
    comment = comment,
    rating = rating,
    status = status,
    service = service,
    user = user,
    totalPrice = totalPrice,
    address = address
)