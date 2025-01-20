package mortum.skufservices.dto

data class SignUpResponse(
    val token: String,
    val userId: String?,
    val username: String,
    val roles: List<String>,
)