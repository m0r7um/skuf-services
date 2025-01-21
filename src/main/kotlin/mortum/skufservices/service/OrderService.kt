package mortum.skufservices.service

import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import mortum.skufservices.dto.GetOrderResponse
import mortum.skufservices.dto.GetServiceResponse
import mortum.skufservices.dto.PageWrapper
import mortum.skufservices.dto.order.AddOrderRequest
import mortum.skufservices.dto.order.AddOrderResponse
import mortum.skufservices.mapper.OrderMapper
import mortum.skufservices.persistence.model.order.*
import mortum.skufservices.persistence.model.user.User
import mortum.skufservices.persistence.repository.order.*
import mortum.skufservices.persistence.repository.service.ServiceRepository
import mortum.skufservices.persistence.repository.user.UserRepository
import mortum.skufservices.service.ServiceModelService.Companion.PAGE_SIZE
import mortum.skufservices.utils.CommonUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.math.ceil

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
    private val orderRepository: OrderRepository,

    private val userRepository: UserRepository,

    private val entityManager: EntityManager,
    private val orderMapper: OrderMapper,
) {

    fun getAll(page: Int, search: String?, status: List<OrderStatus>?): PageWrapper<GetOrderResponse> {
        val builder = entityManager.criteriaBuilder
        val userId = CommonUtils.getUserIdFromSecurityContext()

        val countQuery = builder.createQuery(Long::class.java)
        var root = countQuery.from(Order::class.java)
        var predicates = generatePredicates(builder, root, search, status, userId)
        countQuery.select(builder.count(root)).where(*predicates)
        val countPage = ceil(entityManager.createQuery(countQuery).singleResult / PAGE_SIZE.toDouble()).toInt()

        val criteriaQuery = builder.createQuery(Order::class.java)
        root = criteriaQuery.from(Order::class.java)
        predicates = generatePredicates(builder, root, search, status, userId)
        val select = criteriaQuery.select(root).where() .where(*predicates)
        val orders = entityManager.createQuery(select).setFirstResult((page - 1) * PAGE_SIZE).setMaxResults(PAGE_SIZE).resultList
        return orderMapper.mapToPageOrderResponse(orders, page, countPage)
    }

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

    fun getById(id: String): GetOrderResponse? {
        return orderRepository.findByIdOrNull(id)?.let { orderMapper.mapToGetOrderResponse(it) }
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

    private fun generatePredicates(
        builder: CriteriaBuilder,
        root: Root<Order>,
        search: String?,
        status: List<OrderStatus>?,
        userId: String?,
    ): Array<Predicate> {
        val predicates = mutableListOf<Predicate?>()
        if (search != null) {
            val predicateTitle = builder.like(root.get<String>("service").get("title"), "%$search%")
            val predicateDescription = builder.like(root.get<String>("service").get("description"), "%$search%")
            predicates.add(builder.or(predicateTitle, predicateDescription))
        }
        if (status != null) {
            val predicateType = root.get<String>("status").`in`(status)
            predicates.add(predicateType)
        }
        if (userId != null) {
            val predicateUserId = builder.equal(root.get<String>("user").get<String>("id"), userId)
            predicates.add(predicateUserId)
        }

        return predicates.requireNoNulls().toTypedArray()
    }
}
