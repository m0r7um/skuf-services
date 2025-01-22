package mortum.skufservices.persistence.model.order

import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import mortum.skufservices.persistence.model.service.ServiceModel
import mortum.skufservices.persistence.model.user.User
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.math.BigDecimal
import java.util.UUID

@Entity
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "orders")
abstract class Order(
    @Id
    val id: String = UUID.randomUUID().toString(),

    val comment: String?,

    @Min(0)
    @Max(5)
    val rating: Byte?,

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    var status: OrderStatus,

    @ManyToOne
    val service: ServiceModel,

    @ManyToOne
    val user: User,

    val totalPrice: BigDecimal,
)