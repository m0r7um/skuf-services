package mortum.skufservices.persistence.model.service

import jakarta.persistence.*
import mortum.skufservices.persistence.model.order.Order
import mortum.skufservices.persistence.model.user.User
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.math.BigDecimal

@Entity
@Table(name = "service")
class ServiceModel(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,

    val title: String,

    val description: String,

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    val type: ServiceType,

    @OneToMany
    @JoinColumn(name = "service_id")
    val orders: List<Order>,

    @ManyToOne
    val user: User,

    val price: BigDecimal,
)