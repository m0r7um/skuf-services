package mortum.skufservices.config

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
        val login = ClassPathResource("static/login.html")
        val registerUser = ClassPathResource("static/register-user.html")
        val registerProvider = ClassPathResource("static/register-provider.html")
        val mainProvider = ClassPathResource("static/main.html")
        return route()
            .resource(path("/login"), login)
            .resource(path("/user/signup"), registerUser)
            .resource(path("/provider/signup"), registerProvider)
            .resource(path("/main"), mainProvider)
            .build()
    }
}