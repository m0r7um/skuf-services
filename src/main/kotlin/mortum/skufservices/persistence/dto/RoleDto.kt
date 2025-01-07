package mortum.skufservices.persistence.dto

import mortum.skufservices.persistence.model.user.RoleEnum
import java.util.UUID

data class RoleDto(
    val id: UUID?,
    val name: RoleEnum,
)