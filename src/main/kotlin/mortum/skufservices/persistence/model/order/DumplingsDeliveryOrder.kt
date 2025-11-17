package mortum.skufservices.persistence.model.order

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import mortum.skufservices.persistence.model.service.ServiceModel
import mortum.skufservices.persistence.model.user.User
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.math.BigDecimal

@Entity
@DiscriminatorValue("DUMPLINGS")
class DumplingsDeliveryOrder(
    totalPrice: BigDecimal,
    comment: String?,
    rating: Byte?,
    status: OrderStatus,
    service: ServiceModel,
    user: User,
    address: String?,

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    val dumplingsContent: List<DumplingsOrderItem> = emptyList()
) : Order(
    totalPrice = totalPrice,
    comment = comment,
    rating = rating,
    status = status,
    service = service,
    user = user,
    address = address
)

@Embeddable
data class DumplingsOrderItem(
    @Column(name = "dumpling_id")
    val dumplingId: String,

    val count: Int
) {
    constructor() : this("", 0)
}