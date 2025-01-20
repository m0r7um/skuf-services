package mortum.skufservices.persistence.model.order

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "alcohol_drink")
class AlcoholDrink(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @OneToMany
    @JoinColumn(name = "alcohol_id")
    val orders: Set<AlcoholDeliveryOrderContent>,

    val name: String,

    val description: String,

    val price: BigDecimal,
)
