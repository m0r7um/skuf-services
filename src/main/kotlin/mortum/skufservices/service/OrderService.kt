package mortum.skufservices.service

import mortum.skufservices.dto.order.AddOrderRequest
import mortum.skufservices.dto.order.AddOrderResponse
import mortum.skufservices.mapper.AlcoholDeliveryOrderMapper
import mortum.skufservices.persistence.model.order.AlcoholDeliveryOrder
import mortum.skufservices.persistence.model.order.AlcoholDeliveryOrderContent
import mortum.skufservices.persistence.model.order.OrderStatus
import mortum.skufservices.persistence.repository.order.AlcoholDeliveryOrderContentRepository
import mortum.skufservices.persistence.repository.order.AlcoholDeliveryOrderRepository
import mortum.skufservices.persistence.repository.order.AlcoholDrinkRepository
import mortum.skufservices.persistence.repository.order.AltushkaOrderRepository
import mortum.skufservices.persistence.repository.service.ServiceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class OrderService(
    val altushkaOrderRepository: AltushkaOrderRepository,
    val alcoholDeliveryOrderRepository: AlcoholDeliveryOrderRepository,

    val alcoholDrinkRepository: AlcoholDrinkRepository,

    val alcoholDeliveryOrderContentRepository: AlcoholDeliveryOrderContentRepository,

    val serviceRepository: ServiceRepository,

    val alcoholDeliveryOrderMapper: AlcoholDeliveryOrderMapper,
) {

    fun addOrder(addOrderRequest: AddOrderRequest): AddOrderResponse {
        return when (addOrderRequest) {
            is AddOrderRequest.AddAlcoholDeliveryOrderRequest -> {
                addAlcoholDeliveryOrder(addOrderRequest)
            }
            is AddOrderRequest.AddAltushkaDeliveryOrderRequest -> TODO()
        }
    }

    private fun addAlcoholDeliveryOrder(order: AddOrderRequest.AddAlcoholDeliveryOrderRequest): AddOrderResponse {
        val content = order.content
        val drinkIds = content.keys
        val alcoholDrinkEntities = alcoholDrinkRepository.findAllById(drinkIds)
        val alcoholEntityToCount = alcoholDrinkEntities.map { 
            it to content[it.id]!!
        }

        val serviceId = order.serviceId
        val serviceEntity = serviceRepository.findByIdOrNull(serviceId) ?: throw RuntimeException("Service not found")

        val servicePrice = serviceEntity.price
        val alcoholPriceToCount = alcoholEntityToCount.map { (alcohol, count) ->
            alcohol.price to count
        }
        val totalPrice = calculateOrderTotalPrice(servicePrice, alcoholPriceToCount)
        val orderEntity = AlcoholDeliveryOrder(
            totalPrice = totalPrice,
            content = null,
            comment = null,
            rating = null,
            status = OrderStatus.AWAITING_CONFIRMATION,
            service = serviceEntity,
            // TODO тут полная дичь, создатель заказа - исполнитель услуги, исправить когда сделаем авторизацию и будем юзера тянуть из секьюрити контекста
            user = serviceEntity.user,
        )

        val orderContent = alcoholEntityToCount.filter { it.second > 0 }.map { (alcohol, count) ->
            AlcoholDeliveryOrderContent(
                alcohol = alcohol,
                order = orderEntity,
                count = count,
            )
        }

        alcoholDeliveryOrderRepository.save(orderEntity)

        orderEntity.content = orderContent.toMutableList()
        alcoholDeliveryOrderContentRepository.saveAll(orderContent)

        alcoholDeliveryOrderRepository.save(orderEntity)

        return AddOrderResponse()
    }

    private fun calculateOrderTotalPrice(
        servicePrice: BigDecimal,
        content: List<Pair<BigDecimal, Int>>? = null,
    ): BigDecimal {
        var totalPrice: BigDecimal = servicePrice
        content?.forEach { (alcoholPrice, alcoholCount) ->
            totalPrice = totalPrice.plus(BigDecimal(alcoholCount) * alcoholPrice)
        }
        return totalPrice
    }
}
