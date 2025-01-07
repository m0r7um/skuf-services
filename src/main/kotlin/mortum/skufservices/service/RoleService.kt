package mortum.skufservices.service

import mortum.skufservices.persistence.dto.RoleDto
import mortum.skufservices.persistence.model.user.RoleEnum
import mortum.skufservices.persistence.repository.user.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val roleRepository: RoleRepository,
) {
    fun findByName(role: RoleEnum): RoleDto {
        val roleEntity = roleRepository.findByName(role) ?: throw RuntimeException("Role not found!")
        return RoleDto(roleEntity.id, roleEntity.name)
    }
}