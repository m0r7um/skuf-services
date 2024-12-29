package mortum.skufservices.persistence.model.order

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "dumplings_delivery_order_content")
class DumplingsDeliveryOrderContent(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,

    @ManyToOne
    val order: DumplingsDeliveryOrder,

    @ManyToOne
    val dumplings: Dumplings,

    val count: Int,
)