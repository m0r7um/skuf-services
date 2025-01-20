package mortum.skufservices.persistence.repository.user

import mortum.skufservices.persistence.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun findByLogin(username: String?): User?

    fun existsByLogin(login: String?): Boolean
}