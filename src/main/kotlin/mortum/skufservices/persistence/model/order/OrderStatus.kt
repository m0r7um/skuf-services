package mortum.skufservices.persistence.model.order

enum class OrderStatus {
    CANCELLED,
    PAYMENT_WAITING,
    AWAITING_CONFIRMATION,
    IN_PROGRESS,
    COMPLETED,
}