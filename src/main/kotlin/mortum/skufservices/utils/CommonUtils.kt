package mortum.skufservices.utils

import mortum.skufservices.service.UserDetailsImpl
import org.springframework.security.core.context.SecurityContextHolder

object CommonUtils {
    fun getUserIdFromSecurityContext(): String? {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
        return userDetails.id
    }
}