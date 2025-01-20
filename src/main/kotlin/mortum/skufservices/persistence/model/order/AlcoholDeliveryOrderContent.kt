package mortum.skufservices.persistence.model.order

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "content_of_order_of_alcohol")
class AlcoholDeliveryOrderContent(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne
    val alcohol: AlcoholDrink,

    @ManyToOne
    var order: AlcoholDeliveryOrder,

    val count: Int,
)