package cufa.conecta.com.application.dto.response.usuario

import cufa.conecta.com.model.data.Experiencia
import java.time.LocalDate

data class ExperienciaResponseDto (
    val cargo: String,
    val empresa: String,
    val dtInicio: LocalDate,
    val dtFim: LocalDate
) {
    companion object {
        fun listOf(listaDeExperiencias: List<Experiencia>): List<ExperienciaResponseDto> {
            return listaDeExperiencias.map { data ->
                ExperienciaResponseDto(
                    cargo = data.cargo,
                    empresa = data.empresa,
                    dtInicio = data.dtInicio,
                    dtFim = data.dtFim
                )
            }
        }
    }
}