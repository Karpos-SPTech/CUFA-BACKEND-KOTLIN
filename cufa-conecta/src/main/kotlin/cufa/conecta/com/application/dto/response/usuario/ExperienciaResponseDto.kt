package cufa.conecta.com.application.dto.response.usuario

import java.time.LocalDate

class ExperienciaResponseDto (
    private val cargo: String,
    private val empresa: String,
    private val dtInicio: LocalDate,
    private val dtFim: LocalDate
)