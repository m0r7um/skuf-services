package mortum.skufservices.controller

import mortum.skufservices.dto.GetServiceResponse
import mortum.skufservices.dto.PageWrapper
import mortum.skufservices.persistence.model.service.ServiceType
import mortum.skufservices.service.ServiceModelService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
    ): PageWrapper<GetServiceResponse> {
        return serviceModelService.getAll(page, search, type)
    }

    @GetMapping("/byProvider")
    fun getByProvider(
        @RequestParam("search") search: String?,
        @RequestParam("page") page: Int,
    ): PageWrapper<GetServiceResponse> {
        return serviceModelService.getByProvider(page, search)
    }

    @GetMapping("/{id}")
    fun getServiceById(@PathVariable id: String): GetServiceResponse? {
        return serviceModelService.getById(id)
    }
}