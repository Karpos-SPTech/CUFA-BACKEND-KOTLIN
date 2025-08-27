package cufa.conecta.com.config


import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import java.util.Date
import javax.crypto.SecretKey

@Component
class GerenciadorTokenJwt(){

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Value("\${jwt.validity}")
    private var jwtTokenValidity: Long = 0

    fun getUsernameFromToken(token: String) = getClaimFromToken(token) { it.subject }

    fun getExpirationDateFromToken(token: String): Date = getClaimFromToken(token) { it.expiration }

    fun generateToken(authentication: Authentication): String {
        val now = Date()

        val expiration = Date(now.time + jwtTokenValidity + 10000)

        val authorities = authentication.authorities.joinToString(",") { it.authority }

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim("authorities", authorities)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(parseSecret(), SignatureAlgorithm.HS256)
            .compact()
    }

        fun <T> getClaimFromToken(
            token: String,
            claimsResolver: (Claims) -> T
        ): T {
            val claims = getAllClaimsFromToken(token)
            return claimsResolver(claims)
        }

        fun validateToken(
            token: String,
            userDetails: UserDetails
        ): Boolean {
            val username = getUsernameFromToken(token)
            return username == userDetails.username && !isTokenExpired(token)
        }

        private fun isTokenExpired(token: String): Boolean {
            val expiration = getExpirationDateFromToken(token)
            return expiration.before(Date())
        }

        private fun getAllClaimsFromToken(token: String): Claims =
            Jwts.parserBuilder()
                .setSigningKey(parseSecret())
                .build()
                .parseClaimsJws(token)
                .body

        private fun parseSecret(): SecretKey = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
    }
