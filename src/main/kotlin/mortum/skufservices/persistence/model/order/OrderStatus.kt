package mortum.skufservices.persistence.model.order

enum class OrderStatus {
    PAYMENT_AWAITING,
    AWAITING_CONFIRMATION,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED,
}