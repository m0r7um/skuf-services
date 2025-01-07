package mortum.skufservices.service

import com.fasterxml.jackson.annotation.JsonIgnore
import mortum.skufservices.persistence.model.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serial
import java.util.UUID
import java.util.stream.Collectors

class UserDetailsImpl(
    val id: UUID?,
    private val username: String,
    @field:JsonIgnore private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {
    companion object {
        @Serial
        private const val serialVersionUID = 1L



        fun build(user: User): UserDetailsImpl {
            val authorities: List<GrantedAuthority> = user.roles.stream()
                .map { role -> SimpleGrantedAuthority(role.name.name) }
                .collect(Collectors.toList())

            return UserDetailsImpl(
                id = user.id,
                username = user.login,
                password = user.password,
                authorities = authorities,
            )
        }
    }

    override fun getAuthorities() = authorities
    override fun getPassword() = password
    override fun getUsername() = username
}