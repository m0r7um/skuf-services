package mortum.skufservices.mapper

import mortum.skufservices.dto.GetAlcoholDrinkResponse
import mortum.skufservices.persistence.model.order.AlcoholDrink
import org.springframework.stereotype.Component

@Component
class AlcoholDrinkMapper {
    fun mapToGetAlcoholDrinkResponse(alcoholDrink: AlcoholDrink): GetAlcoholDrinkResponse {
        return GetAlcoholDrinkResponse(
            id = alcoholDrink.id,
            name = alcoholDrink.name,
            price = alcoholDrink.price.toDouble(),
        )
    }
}