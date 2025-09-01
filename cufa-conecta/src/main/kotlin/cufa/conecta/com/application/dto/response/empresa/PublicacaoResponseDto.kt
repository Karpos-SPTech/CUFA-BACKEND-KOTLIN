package cufa.conecta.com.application.dto.response.empresa

import cufa.conecta.com.model.data.Publicacao
import java.time.LocalDateTime

data class PublicacaoResponseDto(
    val titulo: String,
    val descricao: String,
    val tipoContrato: String,
    val dtExpiracao: LocalDateTime,
    val dtPublicacao: LocalDateTime,
    val nomeEmpresa: String
) {
    companion object {
        fun listOf(listaDePublicacoes: List<Publicacao>): List<PublicacaoResponseDto> {
            return listaDePublicacoes.map { data ->
                PublicacaoResponseDto(
                    nomeEmpresa = data.nomeEmpresa!!,
                    titulo = data.titulo!!,
                    descricao = data.descricao!!,
                    tipoContrato = data.tipoContrato!!,
                    dtExpiracao = data.dtExpiracao!!,
                    dtPublicacao = data.dtPublicacao!!
                )
            }
        }
    }
}