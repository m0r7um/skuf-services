package mortum.skufservices.utils

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class AuthEntryPointJwt : AuthenticationEntryPoint {
    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        log.error("unauthorized error: {}", authException.message)
        log.error(authException.toString())
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        val body: MutableMap<String, Any?> = HashMap()
        body["status"] = HttpServletResponse.SC_UNAUTHORIZED
        body["error"] = "Unauthorized"
        body["message"] = authException.message
        body["path"] = request.servletPath

        val mapper = ObjectMapper()
        mapper.writeValue(response.outputStream, body)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(AuthEntryPointJwt::class.java)
    }
}