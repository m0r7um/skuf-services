package mortum.skufservices.controller

import mortum.skufservices.dto.GetServiceResponse
import mortum.skufservices.dto.UpdateServiceRequest
import mortum.skufservices.dto.PageWrapper
import mortum.skufservices.persistence.model.service.ServiceType
import mortum.skufservices.service.ServiceModelService
import org.springframework.web.bind.annotation.*

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

    @PutMapping("/{id}")
    fun updateServiceById(@PathVariable id: String, @RequestBody service: UpdateServiceRequest): GetServiceResponse {
        return serviceModelService.updateServiceById(id, service)
    }
}