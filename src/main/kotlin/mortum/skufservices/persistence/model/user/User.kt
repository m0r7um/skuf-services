package mortum.skufservices.persistence.model.user

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import mortum.skufservices.persistence.model.service.ServiceModel
import java.util.*

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("login"))])
class User(val login: @NotBlank String, val password: @NotBlank String) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role> = mutableSetOf()

    @OneToMany
    @JoinColumn(name = "user_id")
    val services: MutableSet<ServiceModel> = mutableSetOf()
}