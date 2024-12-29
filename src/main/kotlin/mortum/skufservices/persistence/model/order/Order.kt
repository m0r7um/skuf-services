package mortum.skufservices.persistence.model.order

import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import mortum.skufservices.persistence.model.service.Service
import java.util.UUID

@Entity
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "service_order")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    val comment: String,

    @Min(0)
    @Max(5)
    val rating: Byte,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus,

    @ManyToOne
    val service: Service,
)