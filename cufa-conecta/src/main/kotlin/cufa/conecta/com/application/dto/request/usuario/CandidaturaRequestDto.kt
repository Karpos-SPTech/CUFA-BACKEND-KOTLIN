package cufa.conecta.com.application.dto.request.usuario

import cufa.conecta.com.model.data.Candidatura
import jakarta.validation.constraints.NotNull

data class CandidaturaRequestDto(
    @field:NotNull(message = "O id do usuário não pode ser nulo")
    val fkUsuario: Long,
    @field:NotNull(message = "O id da publicação não pode ser nulo")
    val fkPublicacao: Long,
    @field:NotNull(message = "O id da empresa não pode ser nulo")
    val fkEmpresa: Long
) {
    fun toModel() = Candidatura(
        fkUsuario = fkUsuario,
        fkPublicacao = fkPublicacao,
        fkEmpresa = fkEmpresa
    )
}