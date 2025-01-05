package mortum.skufservices

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.web.servlet.function.RequestPredicates.path
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.RouterFunctions.route
import org.springframework.web.servlet.function.ServerResponse


@Configuration
class RouterConfig{
    @Bean
    fun spaRouter(): RouterFunction<ServerResponse> {
        val index = ClassPathResource("static/login.html")
        return route().resource(path("/login"), index).build()
    }
}