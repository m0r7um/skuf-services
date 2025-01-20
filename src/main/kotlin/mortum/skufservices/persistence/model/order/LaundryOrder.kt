package mortum.skufservices.persistence.model.order

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import mortum.skufservices.persistence.model.service.ServiceModel
import mortum.skufservices.persistence.model.user.User

@Entity
@DiscriminatorValue(value = "LAUNDRY")
class LaundryOrder(
    val totalPrice: Double,
    comment: String,
    rating: Byte,
    status: OrderStatus,
    service: ServiceModel,
    user: User,
) : Order(
    comment = comment,
    rating = rating,
    status = status,
    service = service,
    user = user,
)