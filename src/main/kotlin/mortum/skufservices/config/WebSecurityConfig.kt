package mortum.skufservices.config

import mortum.skufservices.service.UserDetailsServiceImpl
import mortum.skufservices.utils.AccessDeniedJwtHandler
import mortum.skufservices.utils.AuthEntryPointJwt
import mortum.skufservices.utils.AuthTokenFilter
import mortum.skufservices.utils.JwtUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableMethodSecurity
class WebSecurityConfig(
    private val userDetailsService: UserDetailsServiceImpl,
    private val jwtUtils: JwtUtils,
    private val unauthorizedHandler: AuthEntryPointJwt,
    private val accessDeniedJwtHandler: AccessDeniedJwtHandler,
) {

    @Bean
    fun authenticationJwtTokenFilter(): AuthTokenFilter {
        return AuthTokenFilter(jwtUtils, userDetailsService)
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()

        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())

        return authProvider
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .cors{ it.disable() }
            .exceptionHandling {
                it.authenticationEntryPoint(unauthorizedHandler)
                it.accessDeniedHandler(accessDeniedJwtHandler)
            }
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/login").permitAll()
                auth.requestMatchers("/auth/**").permitAll()
                auth.requestMatchers("/user/signup").permitAll()
                auth.requestMatchers("/provider/signup").permitAll()
                auth.requestMatchers("/main").hasRole("USER")
                auth.requestMatchers("/user-orders").hasRole("USER")
                auth.requestMatchers("/user/order").hasRole("USER")
                auth.requestMatchers("/service/add").hasRole("PROVIDER")
                auth.requestMatchers("/provider-order").hasRole("PROVIDER")
                auth.requestMatchers("/provider/order").hasRole("PROVIDER")

                .anyRequest().permitAll()
            }

        http.authenticationProvider(authenticationProvider())
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}