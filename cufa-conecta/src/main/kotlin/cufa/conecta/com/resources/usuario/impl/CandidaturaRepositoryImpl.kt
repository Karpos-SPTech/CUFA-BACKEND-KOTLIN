package cufa.conecta.com.resources.usuario.impl

import cufa.conecta.com.application.exception.PageNotFoundException
import cufa.conecta.com.application.exception.PublicacaoNotFoundException
import cufa.conecta.com.application.exception.UsuarioNotFoundException
import cufa.conecta.com.model.data.Candidato
import cufa.conecta.com.model.data.Candidatura
import cufa.conecta.com.model.data.Experiencia
import cufa.conecta.com.model.data.Publicacao
import cufa.conecta.com.model.data.result.CandidaturaResult
import cufa.conecta.com.resources.empresa.dao.EmpresaDao
import cufa.conecta.com.resources.empresa.dao.PublicacaoDao
import cufa.conecta.com.resources.empresa.entity.EmpresaEntity
import cufa.conecta.com.resources.empresa.entity.PublicacaoEntity
import cufa.conecta.com.resources.empresa.exception.EmpresaNotFoundException
import cufa.conecta.com.resources.usuario.CandidaturaRepository
import cufa.conecta.com.resources.usuario.dao.CandidaturaDao
import cufa.conecta.com.resources.usuario.dao.ExperienciaDao
import cufa.conecta.com.resources.usuario.dao.UsuarioDao
import cufa.conecta.com.resources.usuario.entity.CandidaturaEntity
import cufa.conecta.com.resources.usuario.entity.UsuarioEntity
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.Period
import kotlin.math.ceil

@Repository
class CandidaturaRepositoryImpl(
    private val dao: CandidaturaDao,
    private val usuarioDao: UsuarioDao,
    private val publicacaoDao: PublicacaoDao,
    private val empresaDao: EmpresaDao,
    private val experienciaDao: ExperienciaDao
): CandidaturaRepository {

    override fun criarCandidatura(data: Candidatura) {
        val usuario = buscarUsuarioPeloId(data.fkUsuario)
        val publicacao = buscarPublicacaoPeloId(data.fkPublicacao)
        val empresa = buscarEmpresaPeloId(data.fkEmpresa)
        
        val candidaturaEntity = CandidaturaEntity(
            usuario = usuario,
            publicacao = publicacao,
            empresa = empresa,
            candidatura = LocalDate.now()
        )
        
        dao.save(candidaturaEntity)
    }

    override fun listarDadosDaVaga(id: Long, page: Int, size: Int): CandidaturaResult {
        val totalOfUsers = dao.count()

        val totalOfPages = ceil(totalOfUsers.toDouble() / size).toInt()

        if (page > totalOfPages && totalOfUsers >= 0)
            throw PageNotFoundException("A página $page não foi encontrada")

        val candidatos = listarCandidatosPorPublicacao(id, page, size)
        val publicacao = buscarPublicacaoPeloId(id)

        val vaga = CandidaturaResult(
            titulo = publicacao.titulo,
            nomeEmpresa = publicacao.empresa.nome,
            tipoContrato = publicacao.tipoContrato,
            dtPublicacao = publicacao.dtPublicacao,
            dtExpiracao = publicacao.dtExpiracao,
            qtdCandidatos = candidatos.size,
            candidatos = candidatos,
            paginaAtual = page,
            totalDePaginas = totalOfPages,
            totalDeCandidatos = totalOfUsers
        )

        return vaga
    }

    override fun verificarCandidaturaExistente(userId: Long, vagaId: Long): Boolean {
        val usuario = buscarUsuarioPeloId(userId)
        val vaga = buscarPublicacaoPeloId(vagaId)

        return dao.existsByUsuarioAndPublicacao(usuario, vaga)
    }

    override fun listarPublicacoesCandidatadasPorUsuario(id: Long): List<Publicacao> {
        val usuario: UsuarioEntity = buscarUsuarioPeloId(id)
        val listaDeCandidaturasEntity: List<CandidaturaEntity> = dao.findByUsuario(usuario)

        return mapearPublicacoes(listaDeCandidaturasEntity)
    }

    private fun buscarUsuarioPeloId(id: Long): UsuarioEntity =
        usuarioDao.findById(id)
            .orElseThrow { UsuarioNotFoundException("O usuário com o ID:$id não existe") }

    private fun buscarPublicacaoPeloId(id: Long): PublicacaoEntity =
        publicacaoDao.findById(id)
            .orElseThrow { PublicacaoNotFoundException("A publicação com o ID:$id não existe") }

    private fun buscarEmpresaPeloId(id: Long): EmpresaEntity =
        empresaDao.findById(id)
            .orElseThrow { EmpresaNotFoundException("A empresa com o ID:$id não existe") }

    private fun listarCandidatosPorPublicacao(id: Long, page: Int, size: Int): List<Candidato> {
        val offset = (page - 1) * size

        val listaDeUsuariosEntity = usuarioDao.dadosPaginados(id, offset, size)

        return mapearUsuarios(listaDeUsuariosEntity)
    }

    private fun buscarExperienciasDoUsuario(id: Long): List<Experiencia> {
        val experiencias = experienciaDao.findByUsuarioId(id)
            .map { exp ->
                Experiencia(
                    cargo = exp.cargo,
                    empresa = exp.empresa,
                    dtInicio = exp.dtInicio,
                    dtFim = exp.dtFim
                )
            }
        
        return experiencias
    }

    private fun mapearUsuarios(usuariosEntity: List<UsuarioEntity>): List<Candidato> {
        return usuariosEntity.map { usuarioEntity ->
            val experiencias = buscarExperienciasDoUsuario(usuarioEntity.id!!)

            val dtNascUsuario = usuarioEntity.dtNascimento
            val idade = definirIdadeDoUsuario(dtNascUsuario!!)

            Candidato(
                nome = usuarioEntity.nome!!,
                idade = idade,
                biografia = usuarioEntity.biografia!!,
                email = usuarioEntity.email!!,
                telefone = usuarioEntity.telefone!!,
                curriculoUrl = usuarioEntity.curriculoUrl!!,
                experiencias = experiencias
            )
        }
    }

    private fun mapearPublicacoes(candidaturasEntity: List<CandidaturaEntity>): List<Publicacao> {
        return candidaturasEntity.map { candidaturaEntity ->
            val publicacaoEntity = candidaturaEntity.publicacao

            Publicacao(
                empresaId = publicacaoEntity.empresa.idEmpresa!!,
                nomeEmpresa = publicacaoEntity.empresa.nome,
                titulo = publicacaoEntity.titulo,
                descricao = publicacaoEntity.descricao,
                tipoContrato = publicacaoEntity.tipoContrato,
                dtExpiracao = publicacaoEntity.dtExpiracao,
                dtPublicacao = publicacaoEntity.dtPublicacao
            )
        }
    }

    private fun definirIdadeDoUsuario(dtNasc: LocalDate) = Period.between(dtNasc, LocalDate.now()).years
}
