package mortum.skufservices.persistence.dto

data class SignUpRequest (
    val username: String,
    val password: String,
    val role: Set<String>,
)