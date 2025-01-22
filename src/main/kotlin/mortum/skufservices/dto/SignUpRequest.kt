package mortum.skufservices.dto

import java.util.*

data class SignUpRequest(
    val username: String,
    val password: String,
    val role: Set<String>,
    val name: String,
    val surname: String,
    val birthDate: Date,
)