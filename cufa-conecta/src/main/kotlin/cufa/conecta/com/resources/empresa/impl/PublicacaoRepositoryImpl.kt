package cufa.conecta.com.resources.empresa.impl

import cufa.conecta.com.model.data.Publicacao
import cufa.conecta.com.resources.empresa.PublicacaoRepository
import cufa.conecta.com.resources.empresa.dao.EmpresaDao
import cufa.conecta.com.resources.empresa.dao.PublicacaoDao
import cufa.conecta.com.resources.empresa.entity.PublicacaoEntity
import java.time.LocalDateTime

class PublicacaoRepositoryImpl(
    private val dao: PublicacaoDao,
    private val empresaDao: EmpresaDao
): PublicacaoRepository {

    override fun save(data: Publicacao) {
        val empresa = buscarEmpresaPorId(data.empresaId)
        
        val publicacao = PublicacaoEntity(
            empresa = empresa,
            titulo = data.titulo,
            descricao = data.descricao,
            tipoContrato = data.tipoContrato,
            dtExpiracao = data.dtExpiracao,
            dtPublicacao = LocalDateTime.now(),
        )
        
        dao.save(publicacao)
    }

    override fun buscarTodas(): List<Publicacao> {
        val listaDePublicacoesEntity = dao.findAll()

        return mapearPublicacoes(listaDePublicacoesEntity)
    }

    override fun buscarPublicacoesPorEmpresaId(id: Long): List<Publicacao> {
        val listaDePublicacoesEntity = dao.findPublicacoesByEmpresaId(id)

        return mapearPublicacoes(listaDePublicacoesEntity)
    }

    override fun findById(id: Long): Publicacao {
        val entity = buscarPublicacaoPorId(id)

        val publicacao = Publicacao(
            empresaId = entity.empresa.idEmpresa!!,
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
}