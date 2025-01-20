package mortum.skufservices.controller

import mortum.skufservices.dto.GetAlcoholDrinkResponse
import mortum.skufservices.service.AlcoholDrinkService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/alcohol-drinks")
class AlcoholDrinkController(
    val alcoholService: AlcoholDrinkService,
) {
    @GetMapping
    fun getAll(): List<GetAlcoholDrinkResponse> {
        return alcoholService.getAllDrinks()
    }
}