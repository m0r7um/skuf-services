package mortum.skufservices.persistence.model.order

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import mortum.skufservices.persistence.model.service.ServiceModel
import mortum.skufservices.persistence.model.user.User
import java.math.BigDecimal

@Entity
@DiscriminatorValue(value = "LAUNDRY")
class LaundryOrder(
    totalPrice: BigDecimal,
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