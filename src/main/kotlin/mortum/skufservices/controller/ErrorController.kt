package mortum.skufservices.controller

import jakarta.servlet.RequestDispatcher
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ErrorController : ErrorController {
    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest, response: HttpServletResponse): String {
        val status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)


        val statusCode = Integer.valueOf(status.toString())

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            response.sendRedirect("/login")
        }/* else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            response.sendRedirect("/login")
        }*/
        return "error"
    }
}