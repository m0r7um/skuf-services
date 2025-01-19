package mortum.skufservices.controller

import mortum.skufservices.dto.GetPageServiceResponse
import mortum.skufservices.persistence.model.service.ServiceType
import mortum.skufservices.service.ServiceModelService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/service")
class ServiceController(
    val serviceModelService: ServiceModelService
) {

    @GetMapping("/all")
    fun getAll(
        @RequestParam("search") search: String?,
        @RequestParam("type") type: List<ServiceType>?,
        @RequestParam("page") page: Int,
    ): GetPageServiceResponse {
        return serviceModelService.getAll(page, search, type)
    }
}