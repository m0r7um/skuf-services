package mortum.skufservices.service

import mortum.skufservices.dto.GetAlcoholDrinkResponse
import mortum.skufservices.mapper.AlcoholDrinkMapper
import mortum.skufservices.persistence.repository.order.AlcoholDrinkRepository
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration
import org.springframework.stereotype.Service

@Service
class AlcoholDrinkService(
    val alcoholDrinkRepository: AlcoholDrinkRepository,
    val alcoholDrinkMapper: AlcoholDrinkMapper,
) {
    fun getAllDrinks(): List<GetAlcoholDrinkResponse> {
        return alcoholDrinkRepository.findAll().map { alcoholDrink ->
            alcoholDrinkMapper.mapToGetAlcoholDrinkResponse(alcoholDrink)
        }
    }
}