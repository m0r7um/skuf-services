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
)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
sealed class AddOrderRequest {
    abstract val type: String

    data class AddAlcoholDeliveryOrderRequest(
        override val type: String = "ALCOHOL",
        val serviceId: String,
        val content: Map<String, Int>,
    ) : AddOrderRequest()

    data class AddAltushkaDeliveryOrderRequest(
        override val type: String = "ALTUSHKA",
    ) : AddOrderRequest()
}