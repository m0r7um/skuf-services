package mortum.skufservices.controller

import mortum.skufservices.persistence.model.service.ServiceModel
import mortum.skufservices.service.ServiceModelService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceController(
    val serviceModelService: ServiceModelService
) {

    @GetMapping("/service/all")
    fun getAll(): MutableList<ServiceModel> {
        return serviceModelService.getAll();
    }
}