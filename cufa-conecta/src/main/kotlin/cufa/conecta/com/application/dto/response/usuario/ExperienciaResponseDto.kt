package cufa.conecta.com.application.dto.response.usuario

import java.time.LocalDate

class ExperienciaResponseDto (
    val cargo: String,
    val empresa: String,
    val dtInicio: LocalDate,
    val dtFim: LocalDate
)