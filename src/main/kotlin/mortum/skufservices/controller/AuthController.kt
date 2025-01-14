package mortum.skufservices.controller

import jakarta.validation.Valid
import mortum.skufservices.service.UserDetailsImpl
import mortum.skufservices.service.UserService
import mortum.skufservices.utils.JwtUtils
import mortum.skufservices.dto.LoginRequest
import mortum.skufservices.dto.LoginResponse
import mortum.skufservices.dto.SignUpRequest
import mortum.skufservices.dto.SignUpResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,

    private val userService: UserService,

    private val jwtUtils: JwtUtils,
) {

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): LoginResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = jwtUtils.generateJwtToken((authentication.principal as UserDetails).username)

        val userDetails: UserDetailsImpl = authentication.principal as UserDetailsImpl
        val roles: List<String> = userDetails.getAuthorities()
            .map { obj: GrantedAuthority -> obj.authority }
            .toList()

        return LoginResponse(
            jwt,
            userDetails.id,
            userDetails.getUsername(),
            roles,
        )
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signupRequest: @Valid SignUpRequest): SignUpResponse {
        val userDto = userService.signupUser(signupRequest)
        val jwt: String = jwtUtils.generateJwtToken(userDto.username)
        val signupResponse = SignUpResponse(
            token = jwt,
            userId = userDto.id,
            username = userDto.username,
            roles = userDto.roles.map { it.name.name },
        )
        return signupResponse
    }
}