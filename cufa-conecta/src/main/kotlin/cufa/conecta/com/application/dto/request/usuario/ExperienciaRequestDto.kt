package cufa.conecta.com.application.dto.request.usuario

import cufa.conecta.com.model.data.Experiencia
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class ExperienciaRequestDto(
    @field:NotBlank(message = "O campo cargo não pode ser nulo, vazio ou branco")
    val cargo: String,
    @field:NotBlank(message = "O campo empresa não pode ser nulo, vazio ou branco")
    val empresa: String,
    @field:NotBlank(message = "O campo dtInicio não pode ser nulo, vazio ou branco")
    val dtInicio: LocalDate,
    @field:NotBlank(message = "O campo dtFim não pode ser nulo, vazio ou branco")
    val dtFim: LocalDate
) {
    fun toModel() = Experiencia(
        cargo = cargo,
        empresa = empresa,
        dtInicio = dtInicio,
        dtFim = dtFim
    )
}