package mortum.skufservices.mapper

import mortum.skufservices.dto.GetServiceResponse
import mortum.skufservices.dto.UserResponse
import mortum.skufservices.persistence.model.service.ServiceModel
import org.springframework.stereotype.Component

@Component
class ServiceMapper {

    fun mapToGetServiceResponse(serviceModel: ServiceModel): GetServiceResponse {
        return GetServiceResponse(
            id = serviceModel.id,
            title = serviceModel.title,
            description = serviceModel.description,
            type = serviceModel.type.name,
            price = serviceModel.price,
            user = UserResponse(
                login = serviceModel.user.login,
                name = serviceModel.user.name,
                surname = serviceModel.user.surname,
            )
        )
    }

}
