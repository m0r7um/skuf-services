package mortum.skufservices.persistence.model.service

import jakarta.persistence.*
import mortum.skufservices.persistence.model.order.Order
import mortum.skufservices.persistence.model.user.User
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "service")
class ServiceModel(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,

    val name: String,

    val description: String,

    @Enumerated(EnumType.STRING)
    val type: ServiceType,

    @OneToMany
    @JoinColumn(name = "service_id")
    val orders: List<Order>,

    @ManyToOne
    val user: User,

    val price: BigDecimal,
)