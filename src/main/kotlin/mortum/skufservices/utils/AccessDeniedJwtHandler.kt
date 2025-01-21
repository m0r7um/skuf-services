package mortum.skufservices.utils

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class AccessDeniedJwtHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.sendRedirect("/login")
    }

    companion object {
        private val log: org.slf4j.Logger = LoggerFactory.getLogger(JwtUtils::class.java)
    }

}