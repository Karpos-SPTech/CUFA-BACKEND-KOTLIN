package cufa.conecta.com.application.dto.request.empresa

import jakarta.validation.constraints.NotBlank

data class BiografiaRequestDto(
    @field:NotBlank(message = "A biografia não pode ser nulo, vazio ou branco")
    val biografia: String
)