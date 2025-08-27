package cufa.conecta.com.model.data.result

import cufa.conecta.com.model.data.Candidato
import java.time.LocalDateTime

data class CandidaturaResult (
    val titulo: String,
    val nomeEmpresa: String,
    val tipoContrato: String,
    val dtPublicacao: LocalDateTime,
    val dtExpiracao: LocalDateTime,
    val qtdCandidatos: Int,
    val candidatos: List<Candidato>
)