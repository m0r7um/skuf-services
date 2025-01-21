package mortum.skufservices.utils

import mortum.skufservices.service.UserDetailsImpl
import org.springframework.security.core.context.SecurityContextHolder
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object CommonUtils {
    fun getUserIdFromSecurityContext(): String? {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
        return userDetails.id
    }
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("UTC"))
}