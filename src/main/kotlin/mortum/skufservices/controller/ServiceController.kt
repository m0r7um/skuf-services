package mortum.skufservices.controller

import mortum.skufservices.dto.GetServiceResponse
import mortum.skufservices.service.ServiceModelService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/service")
class ServiceController(
    val serviceModelService: ServiceModelService
) {

    @GetMapping("/all")
    fun getAll(): List<GetServiceResponse> {
        val e = serviceModelService.getAll()
        return e
    }

    @GetMapping("/{id}")
    fun getServiceById(@PathVariable id: String): GetServiceResponse? {
        return serviceModelService.getById(id)
    }
}