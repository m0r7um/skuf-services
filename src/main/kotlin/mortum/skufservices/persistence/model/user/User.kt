package mortum.skufservices.persistence.model.user

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.time.Instant
import mortum.skufservices.persistence.model.service.ServiceModel
import java.util.*

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("login"))])
class User(
    val login: @NotBlank String,
    val password: @NotBlank String,
    val name: @NotBlank String,
    val surname: @NotBlank String,
    @Column(name = "birth_date")
    val birthDate: Instant,
) {
    @Id
    var id: String = UUID.randomUUID().toString()

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