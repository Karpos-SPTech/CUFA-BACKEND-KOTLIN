package cufa.conecta.com.model.data.result

import java.time.LocalDate

data class ExperienciaResult(
    val idUsuario: Long,
    val cargo: String,
    val empresa: String,
    val dtInicio: LocalDate,
    val dtFinal: LocalDate,
)