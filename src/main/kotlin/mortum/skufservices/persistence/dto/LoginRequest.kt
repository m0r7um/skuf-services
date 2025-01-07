package mortum.skufservices.persistence.dto

data class LoginRequest(
    val username: String,
    val password: String,
)