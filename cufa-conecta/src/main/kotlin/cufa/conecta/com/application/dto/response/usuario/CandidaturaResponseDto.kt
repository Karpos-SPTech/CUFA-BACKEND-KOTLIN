package cufa.conecta.com.application.dto.response.usuario

import java.time.LocalDateTime

data class CandidaturaResponseDto(
    private val titulo: String,
    private val nomeEmpresa: String,
    private val tipoContrato: String,
    private val dtPublicacao: LocalDateTime,
    private val dtExpiracao: LocalDateTime,
    private val qtdCandidatos: Int,
    private val candidatos: List<CandidaturaResponseDto>,
)