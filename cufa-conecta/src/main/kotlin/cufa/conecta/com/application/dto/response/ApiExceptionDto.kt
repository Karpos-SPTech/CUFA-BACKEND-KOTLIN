package cufa.conecta.com.application.dto.response

import org.springframework.http.HttpStatus

data class ApiExceptionDto(
    val status: HttpStatus,
    val message: String,
    val type: String
)