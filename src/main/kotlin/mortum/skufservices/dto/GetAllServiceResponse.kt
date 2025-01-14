package mortum.skufservices.dto

import java.math.BigDecimal

data class GetAllServiceResponse(
    val id: String,
    val description: String,
    val type: String,
    val price: BigDecimal,
) {
}