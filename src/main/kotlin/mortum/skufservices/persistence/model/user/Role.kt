package mortum.skufservices.persistence.model.user


import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "role")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var name: RoleEnum,
)