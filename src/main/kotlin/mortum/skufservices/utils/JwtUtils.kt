package mortum.skufservices.utils

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtUtils(
    @Value("\${security.token}")
    private val jwtSecret: String,

    @Value("\${security.expiration-time-ms}")
    private val expirationTimeMs: Long,
) {

    fun generateJwtToken(userName: String): String {
        return Jwts.builder()
            .setSubject(userName)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + expirationTimeMs))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact()
    }

    private fun key(): Key {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))
    }

    fun getUsernameFromJwtToken(token: String?): String {
        return Jwts.parserBuilder().setSigningKey(key()).build()
            .parseClaimsJws(token).body.subject
    }

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(authToken)
            return true
        } catch (e: MalformedJwtException) {
            log.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            log.error("Expired JWT token: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            log.error("Unsupported JWT token: {}", e.message)
        } catch (e: IllegalArgumentException) {
            log.error("JWT claims string is empty: {}", e.message)
        }
        return false
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(JwtUtils::class.java)
    }
}