package mortum.skufservices.service

import mortum.skufservices.dto.order.AddOrderRequest
import mortum.skufservices.dto.order.AddOrderResponse
import mortum.skufservices.persistence.model.order.*
import mortum.skufservices.persistence.model.user.User
import mortum.skufservices.persistence.repository.order.*
import mortum.skufservices.persistence.repository.service.ServiceRepository
import mortum.skufservices.persistence.repository.user.UserRepository
import mortum.skufservices.utils.CommonUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class OrderService(
    private val altushkaOrderRepository: AltushkaOrderRepository,
    private val alcoholDeliveryOrderRepository: AlcoholDeliveryOrderRepository,
    private val woTOrderRepository: WoTOrderRepository,
    private val dumplingsDeliveryOrderRepository: DumplingsDeliveryOrderRepository,
    private val laundryOrderRepository: LaundryOrderRepository,

    private val alcoholDrinkRepository: AlcoholDrinkRepository,
    private val dumplingsRepository: DumplingsRepository,

    private val alcoholDeliveryOrderContentRepository: AlcoholDeliveryOrderContentRepository,
    private val dumplingsDeliveryOrderContentRepository: DumplingsDeliveryOrderContentRepository,

    private val serviceRepository: ServiceRepository,

    private val userRepository: UserRepository,
) {

    fun addOrder(addOrderRequest: AddOrderRequest): AddOrderResponse {
        return when (addOrderRequest) {
            is AddOrderRequest.AddAlcoholDeliveryOrderRequest -> {
                addAlcoholDeliveryOrder(addOrderRequest)
            }

            is AddOrderRequest.AddAltushkaDeliveryOrderRequest -> {
                addAltushkaDeliveryOrder(addOrderRequest)
            }

            is AddOrderRequest.AddDumplingsDeliveryOrderRequest -> {
                addDumplingsDeliveryOrderRequest(addOrderRequest)
            }

            is AddOrderRequest.AddWotOrderRequest -> {
                addWotOrder(addOrderRequest)
            }

            is AddOrderRequest.AddLaundryRequest -> {
                addLaundryOrder(addOrderRequest)
            }
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

        val userId = CommonUtils.getUserIdFromSecurityContext()

        val userEntity = userRepository.findByIdOrNull(userId) ?: throw RuntimeException("User not found")

        val orderEntity = AlcoholDeliveryOrder(
            totalPrice = totalPrice,
            content = null,
            comment = null,
            rating = null,
            status = OrderStatus.PAYMENT_AWAITING,
            service = serviceEntity,
            user = userEntity,
        )

        val orderContent = alcoholEntityToCount.filter { it.second > 0 }.map { (alcohol, count) ->
            AlcoholDeliveryOrderContent(
                alcohol = alcohol,
                order = orderEntity,
                count = count,
            )
        }

        alcoholDeliveryOrderRepository.save(orderEntity)
        alcoholDeliveryOrderContentRepository.saveAll(orderContent)

        return AddOrderResponse()
    }

    private fun addWotOrder(addOrderRequest: AddOrderRequest.AddWotOrderRequest): AddOrderResponse {
        val serviceEntity = serviceRepository.findByIdOrNull(addOrderRequest.serviceId) ?: throw RuntimeException("Service not found")

        val userEntity = getUserEntity()

        val orderEntity = WoTOrder(
            comment = null,
            rating = null,
            status = OrderStatus.PAYMENT_AWAITING,
            service = serviceEntity,
            user = userEntity,
            totalPrice = serviceEntity.price,
        )

        woTOrderRepository.save(orderEntity)

        return AddOrderResponse()
    }

    private fun addAltushkaDeliveryOrder(order: AddOrderRequest.AddAltushkaDeliveryOrderRequest): AddOrderResponse {
        val serviceEntity = serviceRepository.findByIdOrNull(order.serviceId) ?: throw RuntimeException("Service not found")

        val userEntity = getUserEntity()

        val orderEntity = AltushkaOrder(
            comment = null,
            rating = null,
            status = OrderStatus.PAYMENT_AWAITING,
            service = serviceEntity,
            user = userEntity,
            totalPrice = serviceEntity.price,
        )

        altushkaOrderRepository.save(orderEntity)

        return AddOrderResponse()
    }

    private fun addDumplingsDeliveryOrderRequest(order: AddOrderRequest.AddDumplingsDeliveryOrderRequest): AddOrderResponse {
        val userEntity = getUserEntity()

        val content = order.content
        val dumplingsIds = content.keys
        val dumplingsEntities = dumplingsRepository.findAllById(dumplingsIds)
        val dumplingsEntityToCount = dumplingsEntities.map {
            it to content[it.id]!!
        }

        val serviceId = order.serviceId
        val serviceEntity = serviceRepository.findByIdOrNull(serviceId) ?: throw RuntimeException("Service not found")

        val servicePrice = serviceEntity.price
        val dumplingsPriceToCount = dumplingsEntityToCount.map { (dumplings, count) ->
            dumplings.price to count
        }

        val totalPrice = calculateOrderTotalPrice(servicePrice, dumplingsPriceToCount)

        val orderEntity = DumplingsDeliveryOrder(
            totalPrice = totalPrice,
            comment = null,
            rating = null,
            status = OrderStatus.PAYMENT_AWAITING,
            service = serviceEntity,
            user = userEntity,
            content = null
        )

        val orderContent = dumplingsEntityToCount.filter { it.second > 0 }.map { (alcohol, count) ->
            DumplingsDeliveryOrderContent(
                dumplings = alcohol,
                order = orderEntity,
                count = count,
            )
        }

        dumplingsDeliveryOrderRepository.save(orderEntity)
        dumplingsDeliveryOrderContentRepository.saveAll(orderContent)

        return AddOrderResponse()
    }

    private fun addLaundryOrder(order: AddOrderRequest.AddLaundryRequest): AddOrderResponse {
        val serviceEntity = serviceRepository.findByIdOrNull(order.serviceId) ?: throw RuntimeException("Service not found")

        val userEntity = getUserEntity()

        val orderEntity = LaundryOrder(
            comment = null,
            rating = null,
            status = OrderStatus.PAYMENT_AWAITING,
            service = serviceEntity,
            user = userEntity,
            totalPrice = serviceEntity.price,
        )

        laundryOrderRepository.save(orderEntity)

        return AddOrderResponse()
    }

    private fun getUserEntity(): User {
        val userId = CommonUtils.getUserIdFromSecurityContext()

        val userEntity = userRepository.findByIdOrNull(userId) ?: throw RuntimeException("User not found")
        return userEntity
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
