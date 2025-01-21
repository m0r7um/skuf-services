package mortum.skufservices.dto.order

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(
        value = AddOrderRequest.AddAlcoholDeliveryOrderRequest::class,
        name = "ALCOHOL",
    ),
    JsonSubTypes.Type(
        value = AddOrderRequest.AddDumplingsDeliveryOrderRequest::class,
        name = "DUMPLINGS",
    ),
    JsonSubTypes.Type(
        value = AddOrderRequest.AddAltushkaDeliveryOrderRequest::class,
        name = "ALTUSHKA",
    ),
    JsonSubTypes.Type(
        value = AddOrderRequest.AddWotOrderRequest::class,
        name = "WOT",
    ),
    JsonSubTypes.Type(
        value = AddOrderRequest.AddLaundryRequest::class,
        name = "LAUNDRY",
    ),
)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
sealed class AddOrderRequest {
    abstract val type: String
    abstract val serviceId: String

    data class AddAlcoholDeliveryOrderRequest(
        override val type: String = "ALCOHOL",
        override val serviceId: String,
        val content: Map<String, Int>,
    ) : AddOrderRequest()

    data class AddDumplingsDeliveryOrderRequest(
        override val type: String = "DUMPLINGS",
        override val serviceId: String,
        val content: Map<String, Int>,
    ) : AddOrderRequest()

    data class AddAltushkaDeliveryOrderRequest(
        override val type: String = "ALTUSHKA",
        override val serviceId: String,
    ) : AddOrderRequest()

    data class AddWotOrderRequest(
        override val type: String = "WOT",
        override val serviceId: String,
    ) : AddOrderRequest()

    data class AddLaundryRequest(
        override val type: String = "LAUNDRY",
        override val serviceId: String,
    ) : AddOrderRequest()
}