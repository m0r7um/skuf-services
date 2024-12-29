package mortum.skufservices.persistence.model.order

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "alcohol_drink")
class AlcoholDrink(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,

    @OneToMany
    @JoinColumn(name = "alcohol_id")
    val orders: Set<AlcoholDeliveryOrderContent>,

    val name: String,

    val description: String,

    val price: Double,
)
