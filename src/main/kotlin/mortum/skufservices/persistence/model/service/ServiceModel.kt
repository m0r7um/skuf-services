package mortum.skufservices.persistence.model.service

import jakarta.persistence.*
import mortum.skufservices.persistence.model.order.Order
import mortum.skufservices.persistence.model.user.User
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "service")
class ServiceModel(
    @Id
    val id: String = UUID.randomUUID().toString(),

    var title: String,

    var description: String,

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    var type: ServiceType,

    @OneToMany
    @JoinColumn(name = "service_id")
    val orders: List<Order>,

    @ManyToOne
    val user: User,

    var price: BigDecimal,
)