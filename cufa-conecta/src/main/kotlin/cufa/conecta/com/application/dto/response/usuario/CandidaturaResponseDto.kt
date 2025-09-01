package cufa.conecta.com.application.dto.response.usuario

import java.time.LocalDateTime

data class CandidaturaResponseDto(
    val titulo: String,
    val nomeEmpresa: String,
    val tipoContrato: String,
    val dtPublicacao: LocalDateTime,
    val dtExpiracao: LocalDateTime,
    val qtdCandidatos: Int,
    val candidatos: List<CandidaturaResponseDto>,
)