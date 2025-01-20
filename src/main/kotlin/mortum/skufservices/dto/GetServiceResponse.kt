package mortum.skufservices.dto

import java.math.BigDecimal

data class GetServiceResponse(
    val id: String,
    val title: String,
    val description: String,
    val type: String,
    val price: BigDecimal,
    val user: UserResponse,
)