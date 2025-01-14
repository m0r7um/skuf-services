package mortum.skufservices.dto

import java.util.UUID

class LoginResponse (
    val token: String,
    val userId: UUID?,
    val username: String,
    val roles: List<String>,
)