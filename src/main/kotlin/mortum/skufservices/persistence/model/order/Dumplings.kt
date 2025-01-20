package mortum.skufservices.persistence.model.order

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "dumplings")
class Dumplings(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @OneToMany
    @JoinColumn(name = "dumplings_id")
    val orders: Set<DumplingsDeliveryOrderContent>,

    val name: String,

    val description: String,

    val price: BigDecimal,
)