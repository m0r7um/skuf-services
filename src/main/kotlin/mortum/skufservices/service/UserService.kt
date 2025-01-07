package mortum.skufservices.service

import mortum.skufservices.UsernameAlreadyExistsException
import mortum.skufservices.persistence.dto.RoleDto
import mortum.skufservices.persistence.model.user.RoleEnum
import mortum.skufservices.persistence.model.user.User
import mortum.skufservices.persistence.repository.user.UserRepository
import mortum.skufservices.persistence.dto.SignUpRequest
import mortum.skufservices.persistence.dto.UserDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleService: RoleService,
) {

    fun signupUser(signupRequest: SignUpRequest): UserDto {
        if (userRepository.existsByLogin(signupRequest.username)) {
            throw UsernameAlreadyExistsException("Username already exists")
        }

        val userDto = UserDto(
            signupRequest.username,
            passwordEncoder.encode(signupRequest.password)
        )
        val strRoles: Set<String> = signupRequest.role
        val roles: MutableSet<RoleDto> = HashSet()

        if (strRoles.isEmpty()) {
            val userRole: RoleDto = roleService.findByName(RoleEnum.ROLE_USER)
            roles.add(userRole)
        } else {
            strRoles.forEach { role ->
                if (role == "admin") {
                    val adminRole: RoleDto = roleService.findByName(RoleEnum.ROLE_ADMIN)
                    roles.add(adminRole)
                } else {
                    val userRole: RoleDto = roleService.findByName(RoleEnum.ROLE_USER)
                    roles.add(userRole)
                }
            }
        }
        userDto.roles = roles

        val user: User = userDto.toUser()

        userRepository.save(user)

        return userDto
    }
}