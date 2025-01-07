package mortum.skufservices.persistence.repository.user

import mortum.skufservices.persistence.model.user.RoleEnum
import mortum.skufservices.persistence.model.user.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: RoleEnum): Role?
}