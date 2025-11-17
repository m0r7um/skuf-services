package mortum.skufservices.persistence.model.order

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import mortum.skufservices.persistence.model.service.ServiceModel
import mortum.skufservices.persistence.model.user.User
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.math.BigDecimal

@Entity
@DiscriminatorValue("ALCOHOL")
class AlcoholDeliveryOrder(
    totalPrice: BigDecimal,
    comment: String?,
    rating: Byte?,
    status: OrderStatus,
    service: ServiceModel,
    user: User,
    address: String?,
    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    val alcoholContent: List<AlcoholOrderItem> = emptyList()
) : Order(
    totalPrice = totalPrice,
    comment = comment,
    rating = rating,
    status = status,
    service = service,
    user = user,
    address = address,
)

@Embeddable
data class AlcoholOrderItem(
    @Column(name = "alcohol_id")
    val alcoholId: String,

    val count: Int
) {
    constructor() : this("", 0)
}