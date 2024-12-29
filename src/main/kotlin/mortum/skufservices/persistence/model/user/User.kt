package mortum.skufservices.persistence.model.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Id
    val id: UUID,

    @Column(nullable = false, unique = true)
    val login: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val surname: String,

    val birth: Instant,
)