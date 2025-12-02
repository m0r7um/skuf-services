package mortum.skufservices.dto

import mortum.skufservices.persistence.model.user.RoleEnum

data class RoleDto(
    val id: String?,
    val name: RoleEnum,
)