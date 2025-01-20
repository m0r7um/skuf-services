package mortum.skufservices.persistence.model.order

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import mortum.skufservices.persistence.model.service.ServiceModel
import mortum.skufservices.persistence.model.user.User
import java.math.BigDecimal

@Entity
@DiscriminatorValue(value = "ALCOHOL")
class AlcoholDeliveryOrder(
    totalPrice: BigDecimal,
    @OneToMany
    @JoinColumn(name = "order_id")
    var content: MutableList<AlcoholDeliveryOrderContent>?,
    comment: String?,
    rating: Byte?,
    status: OrderStatus,
    service: ServiceModel,
    user: User,
) : Order(
    comment = comment,
    rating = rating,
    status = status,
    service = service,
    user = user,
    totalPrice = totalPrice,
)