package cufa.conecta.com.resources.empresa.impl

import cufa.conecta.com.application.exception.PageNotFoundException
import cufa.conecta.com.model.data.Publicacao
import cufa.conecta.com.model.data.result.PublicacaoResult
import cufa.conecta.com.resources.empresa.PublicacaoRepository
import cufa.conecta.com.resources.empresa.dao.EmpresaDao
import cufa.conecta.com.resources.empresa.dao.PublicacaoDao
import cufa.conecta.com.resources.empresa.entity.PublicacaoEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import kotlin.math.ceil

@Repository
class PublicacaoRepositoryImpl(
    private val dao: PublicacaoDao,
    private val empresaDao: EmpresaDao
): PublicacaoRepository {

    override fun criar(data: Publicacao) {
        val empresa = buscarEmpresaPorId(data.empresaId!!)
        
        val publicacao = PublicacaoEntity(
            empresa = empresa,
            titulo = data.titulo!!,
            descricao = data.descricao!!,
            tipoContrato = data.tipoContrato!!,
            dtExpiracao = data.dtExpiracao!!,
            dtPublicacao = LocalDateTime.now(),
        )
        
        dao.save(publicacao)
    }

    override fun buscarTodas(page: Int, size: Int): PublicacaoResult {
        val totalOfPublishes = dao.count()

        val totalOfPages = ceil(totalOfPublishes.toDouble() / size).toInt()

        if (page > totalOfPages && totalOfPublishes >= 0)
            throw PageNotFoundException("A página $page não foi encontrada")

        val publicacoes = listarPublicacoes(page, size)

        val dadosPaginados = PublicacaoResult(
            paginaAtual = page,
            totalDePaginas = totalOfPages,
            totalDePublicacoes = totalOfPublishes,
            publicacoes = publicacoes
        )

        return dadosPaginados
    }

    override fun buscarPublicacoesPorEmpresaEmail(data: String): List<Publicacao> {
        val listaDePublicacoesEntity = dao.findPublicacoesByEmpresaEmail(data)

        return mapearPublicacoes(listaDePublicacoesEntity)
    }

    override fun findById(id: Long): Publicacao {
        val entity = buscarPublicacaoPorId(id)

        val publicacao = Publicacao(
            empresaId = entity.empresa.idEmpresa!!,
            nomeEmpresa = entity.empresa.nome,
            titulo = entity.titulo,
            descricao = entity.descricao,
            tipoContrato = entity.tipoContrato,
            dtExpiracao = entity.dtExpiracao,
            dtPublicacao = entity.dtPublicacao
        )

        return publicacao
    }

    override fun delete(id: Long) {
        val publicacao = buscarPublicacaoPorId(id)

        dao.delete(publicacao)
    }

    private fun mapearPublicacoes(publicacoesEntity: List<PublicacaoEntity>): List<Publicacao> {
        return publicacoesEntity.map { entity ->
            Publicacao(
                empresaId = entity.empresa.idEmpresa!!,
                nomeEmpresa = entity.empresa.nome,
                titulo = entity.titulo,
                descricao = entity.descricao,
                tipoContrato = entity.tipoContrato,
                dtExpiracao = entity.dtExpiracao,
                dtPublicacao = entity.dtPublicacao
            )
        }
    }

    private fun buscarEmpresaPorId(id: Long) =
        empresaDao.findById(id)
            .orElseThrow { RuntimeException("Empresa not found!") }

    private fun buscarPublicacaoPorId(id: Long) =
        dao.findById(id)
            .orElseThrow { RuntimeException("Empresa not found!") }

    private fun listarPublicacoes(page: Int, size: Int): List<Publicacao> {
        val offset = (page - 1) * size

        val listaDeUsuariosEntity = dao.dadosPaginados(offset, size)

        return mapearPublicacoes(listaDeUsuariosEntity)
    }
}