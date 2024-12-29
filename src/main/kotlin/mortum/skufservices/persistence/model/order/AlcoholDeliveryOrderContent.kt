package mortum.skufservices.persistence.model.order

import jakarta.persistence.*
import java.util.*

@Entity
class AlcoholDeliveryOrderContent(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,

    @ManyToOne
    val alcohol: AlcoholDrink,

    @ManyToOne
    val order: AlcoholDeliveryOrder,

    val count: Int,
)