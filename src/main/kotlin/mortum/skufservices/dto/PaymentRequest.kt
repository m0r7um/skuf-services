package mortum.skufservices.dto

data class PaymentRequest(
    val cardNumber: String,
    val month: String,
    val year: String,
    val code: String,
    val save: Boolean,
)