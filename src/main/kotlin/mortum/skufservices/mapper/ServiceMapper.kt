package mortum.skufservices.mapper

import mortum.skufservices.dto.GetAllServiceResponse
import mortum.skufservices.persistence.model.service.ServiceModel
import org.springframework.stereotype.Component

@Component
class ServiceMapper {

    fun mapToAllServiceResponse(services: List<ServiceModel>): List<GetAllServiceResponse> {
        return services.map {
            GetAllServiceResponse(
                id = it.id,
                description = it.description,
                type = it.type.name,
                price = it.price,
            )
        }

    }
}