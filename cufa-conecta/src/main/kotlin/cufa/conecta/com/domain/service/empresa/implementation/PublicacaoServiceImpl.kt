package cufa.conecta.com.domain.service.empresa.implementation

import cufa.conecta.com.application.exception.InvalidPageNumberException
import cufa.conecta.com.application.exception.InvalidSizeNumberException
import cufa.conecta.com.domain.service.empresa.PublicacaoService
import cufa.conecta.com.model.data.Publicacao
import cufa.conecta.com.model.data.result.PublicacaoResult
import cufa.conecta.com.resources.empresa.PublicacaoRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class PublicacaoServiceImpl(
    private val repository: PublicacaoRepository
): PublicacaoService {
    override fun criar(data: Publicacao) = repository.criar(data)

    override fun buscarTodas(page: Int, size: Int): PublicacaoResult {
        validatePageAndSize(page, size)

        return repository.buscarTodas(page, size)
    }

    override fun buscarPublicacoesDaEmpresa(): List<Publicacao> {
        val emailEmpresaLogada = SecurityContextHolder.getContext().authentication.name

        val publicacoes = repository.buscarPublicacoesPorEmpresaEmail(emailEmpresaLogada)

        return publicacoes
    }

    override fun findById(id: Long): Publicacao = repository.findById(id)

    override fun editarPublicacao(id: Long, data: Publicacao) {
        val publicacao = repository.findById(id)

        val publicacaoEditada = Publicacao(
            titulo = data.titulo ?: publicacao.titulo,
            descricao = data.descricao ?: publicacao.descricao,
            tipoContrato = data.tipoContrato ?: publicacao.tipoContrato,
            dtExpiracao = data.dtExpiracao ?: publicacao.dtExpiracao
        )

        repository.criar(publicacaoEditada)
    }

    override fun delete(id: Long) = repository.delete(id)

    private fun validatePageAndSize(page: Int, size: Int) {
        if (page < 1) throw InvalidPageNumberException("A página $page não foi encontrada")

        if (size < 1) throw InvalidSizeNumberException("O tamanho da lista desejado é inválido")
    }
}