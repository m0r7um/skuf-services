package mortum.skufservices.persistence.dto

import jakarta.validation.constraints.NotBlank
import mortum.skufservices.persistence.model.user.Role
import mortum.skufservices.persistence.model.user.User
import java.time.Instant
import java.util.*

data class UserDto(
    val username: String,
    val password: String,
    val name: @NotBlank String,
    val surname: @NotBlank String,
    val birthDate: Instant,
) {
    val id: UUID? = null

    var roles: Set<RoleDto> = HashSet()

    fun toUser(): User {
        val rolesDto: Set<RoleDto> = this.roles
        val roles = rolesDto.map { roleDto: RoleDto ->
            Role(
                roleDto.id,
                roleDto.name,
            )
        }.toMutableSet()
        val user = User(
            login = this.username,
            password = this.password,
            name = this.name,
            surname = this.surname,
            birthDate = this.birthDate,
        )
        user.roles = roles
        user.id = this.id
        return user
    }
}