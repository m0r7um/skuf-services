package mortum.skufservices.mapper

import mortum.skufservices.dto.CreateServiceRequest
import mortum.skufservices.dto.GetServiceResponse
import mortum.skufservices.dto.PageWrapper
import mortum.skufservices.dto.UserResponse
import mortum.skufservices.persistence.model.service.ServiceModel
import mortum.skufservices.persistence.model.user.User
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class ServiceMapper {

    fun mapToGetPageServiceResponse(services: List<ServiceModel>, currentPage: Int, countPage: Int): PageWrapper<GetServiceResponse> {
        return PageWrapper(
            data = services.map { mapToGetServiceResponse(it) },
            currentPage = currentPage,
            totalPage = countPage,
        )
    }

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

    fun mapToServiceModel(user: User, request: CreateServiceRequest): ServiceModel{
        return ServiceModel(
            title = request.title,
            description = request.description,
            type = request.type,
            user = user,
            price = BigDecimal(request.price),
        )
    }

}
