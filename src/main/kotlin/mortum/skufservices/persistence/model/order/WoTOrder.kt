package mortum.skufservices.persistence.model.order

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import mortum.skufservices.persistence.model.service.ServiceModel
import mortum.skufservices.persistence.model.user.User
import java.math.BigDecimal

@Entity
@DiscriminatorValue(value = "WOT")
class WoTOrder(
    comment: String?,
    rating: Byte?,
    status: OrderStatus,
    service: ServiceModel,
    user: User,
    totalPrice: BigDecimal,
    address: String,
) : Order(
    comment = comment,
    rating = rating,
    status = status,
    service = service,
    user = user,
    totalPrice = totalPrice,
    address = address,
)