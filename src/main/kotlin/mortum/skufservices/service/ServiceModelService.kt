package mortum.skufservices.service

import mortum.skufservices.dto.GetServiceResponse
import mortum.skufservices.mapper.ServiceMapper
import mortum.skufservices.persistence.repository.order.OrderRepository
import org.springframework.stereotype.Service

@Service
class ServiceModelService(
    val orderRepository: OrderRepository,
    val serviceMapper: ServiceMapper,
) {

    fun getAll(): List<GetServiceResponse> {
        return orderRepository.findAll().map { serviceMapper.mapToGetServiceResponse(it) }
    }
}