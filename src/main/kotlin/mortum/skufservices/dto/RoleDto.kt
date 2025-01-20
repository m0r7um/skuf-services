package mortum.skufservices.dto

import mortum.skufservices.persistence.model.user.RoleEnum
import java.util.UUID

data class RoleDto(
    val id: String?,
    val name: RoleEnum,
)