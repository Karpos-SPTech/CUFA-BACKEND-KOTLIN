package cufa.conecta.com.config.security

import org.springframework.http.ResponseCookie

private const val EXPIRATION = 3600L

fun jwtCookie(token: String) = ResponseCookie.from("jwt", token)
    .httpOnly(true)
    .secure(true)      // necessário p/ SameSite=None
    .path("/")
    .maxAge(EXPIRATION)
    .sameSite("None")
    .build()

fun clearJwtCookie() = ResponseCookie.from("jwt", "")
    .httpOnly(true)
    .secure(true)
    .path("/")
    .maxAge(0)
    .sameSite("None")
    .build()