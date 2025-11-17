package mortum.skufservices.service

import mortum.skufservices.dto.PaymentRequest
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder

@Service
class PaymentService(
    private val restClient: RestClient,
) {

    fun processPayment(request: PaymentRequest) {
        val uri = UriComponentsBuilder
            .fromUriString("http://localhost:8081/payment")
            .apply {
                queryParam("code", request.code)
            }
            .encode()
            .build()
            .toUri()
        restClient.post()
            .uri(uri)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toBodilessEntity()
    }
}