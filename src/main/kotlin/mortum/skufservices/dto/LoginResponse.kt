package mortum.skufservices.dto


class LoginResponse (
    val token: String,
    val userId: String?,
    val username: String,
    val roles: List<String>,
)