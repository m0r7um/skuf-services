package mortum.skufservices.persistence.model.user


import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "role")
class Role(
    @Id
    val id: String? = UUID.randomUUID().toString(),

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var name: RoleEnum,
)