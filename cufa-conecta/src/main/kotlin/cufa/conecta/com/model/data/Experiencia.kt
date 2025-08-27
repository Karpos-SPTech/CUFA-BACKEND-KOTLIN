package cufa.conecta.com.model.data

import java.time.LocalDate

data class Experiencia (
    val id: Long?= null,
    val usuarioId: Long? = null,
    val cargo: String,
    val empresa: String,
    val dtInicio: LocalDate,
    val dtFim: LocalDate
)