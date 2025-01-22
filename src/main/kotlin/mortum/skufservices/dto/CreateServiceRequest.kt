package mortum.skufservices.dto

import mortum.skufservices.persistence.model.service.ServiceType

data class CreateServiceRequest(
    val description: String,
    val price: Double,
    val title: String,
    val type: ServiceType,
)
