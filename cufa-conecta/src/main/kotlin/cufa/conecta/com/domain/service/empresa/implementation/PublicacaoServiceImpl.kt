package cufa.conecta.com.domain.service.empresa.implementation

import cufa.conecta.com.domain.service.empresa.PublicacaoService
import cufa.conecta.com.model.data.Publicacao
import cufa.conecta.com.resources.empresa.PublicacaoRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class PublicacaoServiceImpl(
    private val repository: PublicacaoRepository
): PublicacaoService {
    override fun criar(data: Publicacao) = repository.criar(data)

    override fun buscarTodas(): List<Publicacao> = repository.buscarTodas()

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
}