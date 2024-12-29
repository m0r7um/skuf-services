package mortum.skufservices.persistence.model.order

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "dumplings")
class Dumplings(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,

    @OneToMany
    @JoinColumn(name = "dumplings_id")
    val orders: Set<DumplingsDeliveryOrderContent>,

    val name: String,

    val description: String,

    val price: Double,
)