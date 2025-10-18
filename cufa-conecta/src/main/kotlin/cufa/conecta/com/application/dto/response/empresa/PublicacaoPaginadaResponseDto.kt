package cufa.conecta.com.application.dto.response.empresa

import cufa.conecta.com.model.data.Publicacao
import cufa.conecta.com.model.data.result.PublicacaoResult

data class PublicacaoPaginadaResponseDto(
    val publicacoes: List<Publicacao>,
    val paginaAtual: Int,
    val totalDePaginas: Int,
    val totalDePublicacoes: Long
) {
    companion object {
        fun listOfResult(publicacaoPaginada: PublicacaoResult): PublicacaoPaginadaResponseDto {
            return PublicacaoPaginadaResponseDto(
                publicacoes = publicacaoPaginada.publicacoes,
                paginaAtual = publicacaoPaginada.paginaAtual,
                totalDePaginas = publicacaoPaginada.totalDePaginas,
                totalDePublicacoes = publicacaoPaginada.totalDePublicacoes
            )
        }
    }
}