package mortum.skufservices.persistence.dto

import java.time.Instant

data class SignUpRequest(
    val username: String,
    val password: String,
    val role: Set<String>,
    val name: String,
    val surname: String,
    val birthDate: Instant,
)