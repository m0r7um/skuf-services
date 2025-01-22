package mortum.skufservices.mapper

import mortum.skufservices.dto.GetOrderResponse
import mortum.skufservices.dto.PageWrapper
import mortum.skufservices.dto.UserResponse
import mortum.skufservices.persistence.model.order.Order
import mortum.skufservices.persistence.model.user.User
import org.springframework.stereotype.Component

@Component
class OrderMapper {

    fun mapToPageOrderResponse(orders: List<Order>, currentPage: Int, countPage: Int): PageWrapper<GetOrderResponse> {
        return PageWrapper(
            data = orders.map { mapToGetOrderResponse(it) },
            currentPage = currentPage,
            totalPage = countPage,
        )
    }

    fun mapToGetOrderResponse(order: Order): GetOrderResponse {
        val provider = order.service.user
        return GetOrderResponse(
            id = order.id,
            type = order.service.type,
            title = order.service.title,
            description = order.service.description,
            status = order.status,
            totalPrice = order.totalPrice,
            user = UserResponse(
                login = order.user.login,
                name = order.user.name,
                surname = order.user.surname,
            ),
            provider = UserResponse(
                login = provider.login,
                name = provider.name,
                surname = provider.surname,
            ),
            address = order.address,
        )
    }
}