package mortum.skufservices.persistence.repository.user

import mortum.skufservices.persistence.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByLogin(username: String?): User?

    fun existsByLogin(login: String?): Boolean
}