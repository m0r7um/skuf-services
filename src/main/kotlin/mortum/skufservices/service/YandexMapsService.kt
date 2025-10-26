package mortum.skufservices.service

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder

@Service
class YandexMapsService(
    @Value("\${yandex-maps.api-key}")
    private val apiKey: String,
    private val restClient: RestClient,
) {

    @CircuitBreaker(name = "yandex-maps")
    fun getAddressSuggestions(query: String): Map<String, Any> {
        // https://suggest-maps.yandex.ru/v1/suggest?apikey=${apiKey}&text=${encodeURIComponent(query)}&lang=ru&results=5
        val uri = UriComponentsBuilder
            .fromUriString("https://suggest-maps.yandex.ru/v1/suggest")
            .apply {
                queryParam("text", query)
                queryParam("apikey", apiKey)
                queryParam("lang", "ru")
                queryParam("results", "5")
            }
            .encode()
            .build()
            .toUri()
        return restClient.get()
            .uri(uri)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(object : ParameterizedTypeReference<Map<String, Any>>() {})
            ?: throw RuntimeException("Empty body yandes maps")
    }
}