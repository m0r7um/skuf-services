package mortum.skufservices.persistence.dto

import mortum.skufservices.persistence.model.user.Role
import mortum.skufservices.persistence.model.user.User
import java.util.*

data class UserDto(val username: String, val password: String) {
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
            this.username,
            this.password,
        )
        user.roles = roles
        user.id = this.id
        return user
    }
}