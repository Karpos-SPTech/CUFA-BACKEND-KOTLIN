package cufa.conecta.com.model.data

import java.time.LocalDate

data class Candidatura (
    val fkUsuario: Long?= null,
    val fkPublicacao: Long?= null,
    val fkEmpresa: Long?= null,
    val dtCandidatura: LocalDate
)