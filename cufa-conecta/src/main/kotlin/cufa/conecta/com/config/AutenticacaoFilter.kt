package cufa.conecta.com.config

import cufa.conecta.com.resources.AutenticacaoRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class AutenticacaoFilter(
    private val authentication: AutenticacaoRepository,
    private val jwtTokenManager: GerenciadorTokenJwt
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestTokenHeader = request.getHeader("Authorization")

        if (isBearerToken(requestTokenHeader)) {
            val jwtToken = extractToken(requestTokenHeader!!)

            val username = runCatching { jwtTokenManager.getUsernameFromToken(jwtToken) }
                .getOrElse { throw UserNotFoundException("User not found") }

            if (SecurityContextHolder.getContext().authentication == null) addUsernameInContext(request, username, jwtToken)
        }
        filterChain.doFilter(request, response)
    }

    private fun addUsernameInContext(
        request: HttpServletRequest,
        username: String,
        jwtToken: String
    ) {
        val userDetails: UserDetails = authentication.loadUserByUsername(username)

        if (jwtTokenManager.validateToken(jwtToken, userDetails)) {
            val authToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

            authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

            SecurityContextHolder.getContext().authentication = authToken
        }
    }

    private fun isBearerToken(tokenHeader: String?): Boolean {
        return tokenHeader != null && tokenHeader.startsWith("Bearer ")
    }

    private fun extractToken(tokenHeader: String): String = tokenHeader.substring(7)
}