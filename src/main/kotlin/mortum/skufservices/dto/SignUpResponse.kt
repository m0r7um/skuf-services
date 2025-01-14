package mortum.skufservices.dto

import java.util.*

data class SignUpResponse(
    val token: String,
    val userId: UUID?,
    val username: String,
    val roles: List<String>,
)