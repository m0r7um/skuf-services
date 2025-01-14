package mortum.skufservices.service

import mortum.skufservices.persistence.model.service.ServiceModel
import mortum.skufservices.persistence.repository.order.OrderRepository
import org.springframework.stereotype.Service

@Service
class ServiceModelService(
    val orderRepository: OrderRepository,
) {

    fun getAll(): MutableList<ServiceModel> {
        return orderRepository.findAll()
    }
}