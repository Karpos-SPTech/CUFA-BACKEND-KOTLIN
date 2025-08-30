package cufa.conecta.com.resources.usuario.impl

import cufa.conecta.com.model.data.Candidato
import cufa.conecta.com.model.data.Candidatura
import cufa.conecta.com.model.data.Experiencia
import cufa.conecta.com.model.data.result.CandidaturaResult
import cufa.conecta.com.model.data.result.PublicacaoResult
import cufa.conecta.com.resources.empresa.dao.EmpresaDao
import cufa.conecta.com.resources.empresa.dao.PublicacaoDao
import cufa.conecta.com.resources.empresa.entity.EmpresaEntity
import cufa.conecta.com.resources.empresa.entity.PublicacaoEntity
import cufa.conecta.com.resources.empresa.exception.EmailAlreadyExistsException
import cufa.conecta.com.resources.usuario.CandidaturaRepository
import cufa.conecta.com.resources.usuario.dao.CandidaturaDao
import cufa.conecta.com.resources.usuario.dao.ExperienciaDao
import cufa.conecta.com.resources.usuario.dao.UsuarioDao
import cufa.conecta.com.resources.usuario.entity.CandidaturaEntity
import cufa.conecta.com.resources.usuario.entity.UsuarioEntity
import java.time.LocalDate
import java.time.Period

class CandidaturaRepositoryImpl(
    private val dao: CandidaturaDao,
    private val usuarioDao: UsuarioDao,
    private val publicacaoDao: PublicacaoDao,
    private val empresaDao: EmpresaDao,
    private val experienciaDao: ExperienciaDao
): CandidaturaRepository {

    override fun criarCandidatura(data: Candidatura) {
        val usuario = buscarUsuarioPeloId(data.fkUsuario!!)
        val publicacao = buscarPublicacaoPeloId(data.fkPublicacao!!)
        val empresa = buscarEmpresaPeloId(data.fkEmpresa!!)
        
        val candidaturaEntity = CandidaturaEntity(
            usuario = usuario,
            publicacao = publicacao,
            empresa = empresa,
            candidatura = LocalDate.now()
        )
        
        dao.save(candidaturaEntity)
    }

    override fun listarDadosDaVaga(id: Long): CandidaturaResult {
        val candidatos = listarCandidatosPorPublicacao(id)
        val publicacao = buscarPublicacaoPeloId(id)

        val vaga = CandidaturaResult(
            titulo = publicacao.titulo,
            nomeEmpresa = publicacao.empresa.nome,
            tipoContrato = publicacao.tipoContrato,
            dtPublicacao = publicacao.dtPublicacao,
            dtExpiracao = publicacao.dtExpiracao,
            qtdCandidatos = candidatos.size,
            candidatos = candidatos
        )

        return vaga
    }

    override fun verificarCandidaturaExistente(userId: Long, vagaId: Long): Boolean {
        val usuario = buscarUsuarioPeloId(userId)
        val vaga = buscarPublicacaoPeloId(vagaId)

        return dao.existsByUsuarioAndPublicacao(usuario, vaga)
    }

    override fun listarPublicacoesCandidatadasPorUsuario(id: Long): List<PublicacaoResult> {
        val usuario: UsuarioEntity = buscarUsuarioPeloId(id)
        val listaDeCandidaturasEntity: List<CandidaturaEntity> = dao.findByUsuario(usuario)

        return mapearPublicacoes(listaDeCandidaturasEntity)
    }

    private fun buscarUsuarioPeloId(id: Long): UsuarioEntity =
        usuarioDao.findById(id)
            .orElseThrow { EmailAlreadyExistsException("a") }

    private fun buscarPublicacaoPeloId(id: Long): PublicacaoEntity =
        publicacaoDao.findById(id)
            .orElseThrow { EmailAlreadyExistsException("a") }

    private fun buscarEmpresaPeloId(id: Long): EmpresaEntity =
        empresaDao.findById(id)
            .orElseThrow { EmailAlreadyExistsException("a") }

    private fun listarCandidatosPorPublicacao(id: Long): List<Candidato> {
        val listaDeUsuariosEntity = usuarioDao.findUsuariosByPublicacaoId(id)

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
                nome = usuarioEntity.nome,
                idade = idade,
                biografia = usuarioEntity.biografia!!,
                email = usuarioEntity.email,
                telefone = usuarioEntity.telefone!!,
                curriculoUrl = usuarioEntity.curriculoUrl!!,
                experiencias = experiencias
            )
        }
    }

    private fun mapearPublicacoes(candidaturasEntity: List<CandidaturaEntity>): List<PublicacaoResult> {
        return candidaturasEntity.map { candidaturaEntity ->
            val publicacaoEntity = candidaturaEntity.publicacao

            PublicacaoResult(
                titulo = publicacaoEntity.titulo,
                descricao = publicacaoEntity.descricao,
                tipoContrato = publicacaoEntity.tipoContrato,
                dtExpiracao = publicacaoEntity.dtExpiracao,
                dtPublicacao = publicacaoEntity.dtPublicacao,
                nomeEmpresa = publicacaoEntity.empresa.nome,
                fkEmpresa = publicacaoEntity.empresa.idEmpresa!!
            )
        }
    }

    private fun definirIdadeDoUsuario(dtNasc: LocalDate) = Period.between(dtNasc, LocalDate.now()).years
}
