package cufa.conecta.com.model.data

import java.time.LocalDate

data class Candidatura (
    val fkPublicacao: Long,
    val fkEmpresa: Long,
    val dtCandidatura: LocalDate? = LocalDate.now()
)