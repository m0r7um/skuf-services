package mortum.skufservices.controller

import mortum.skufservices.service.YandexMapsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/address-suggestions")
class YandexMapsController(
    private val yandexMapsService: YandexMapsService,
) {
    @GetMapping
    fun getAddressSuggestions(@RequestParam("query") query: String): Map<String, Any> {
        return yandexMapsService.getAddressSuggestions(query)
    }
}