package mortum.skufservices.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.web.servlet.function.RequestPredicates.path
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.RouterFunctions.route
import org.springframework.web.servlet.function.ServerResponse


@Configuration
class RouterConfig {
    @Bean
    fun spaRouter(): RouterFunction<ServerResponse> {
        val login = ClassPathResource("static/login.html")
        val registerUser = ClassPathResource("static/register-user.html")
        val registerProvider = ClassPathResource("static/register-provider.html")
        val getService = ClassPathResource("static/user-get-service.html")
        val getOrder = ClassPathResource("static/get-order.html")
        val main = ClassPathResource("static/main.html")
        val providerMain = ClassPathResource("static/provider-service.html")
        val providerOrder = ClassPathResource("static/provider-order.html")
        val payment = ClassPathResource("static/payment.html")
        val addService = ClassPathResource("static/add-service.html")
        val editService = ClassPathResource("static/edit-service.html")
        return route()
            .resource(path("/login"), login)
            .resource(path("/user/signup"), registerUser)
            .resource(path("/provider/signup"), registerProvider)
            .resource(path("user/service"), getService)
            .resource(path("user/order"), getOrder)
            .resource(path("/main"), main)
            .resource(path("/provider-service"), providerMain)
            .resource(path("/provider-order"), providerOrder)
            .resource(path("/payment"), payment)
            .resource(path("/service/add"), addService)
            .resource(path("/service/edit"), editService)
            .build()
    }
}