package mortum.skufservices.persistence.model.user


import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.*

@Entity
@Table(name = "role")
class Role(
    @Id
    val id: String? = UUID.randomUUID().toString(),

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    var name: RoleEnum,
)