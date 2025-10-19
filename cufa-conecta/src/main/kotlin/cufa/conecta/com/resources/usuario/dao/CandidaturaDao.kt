package cufa.conecta.com.resources.usuario.dao

import cufa.conecta.com.resources.empresa.entity.PublicacaoEntity
import cufa.conecta.com.resources.usuario.entity.CandidaturaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CandidaturaDao : JpaRepository<CandidaturaEntity, Long> {
    fun existsByUsuarioIdAndPublicacao(usuarioId: Long, vaga: PublicacaoEntity): Boolean

    fun findByUsuarioId(usuarioId: Long): List<CandidaturaEntity>
//
//    @Query("")
//    fun criarCandidatura(
//        idUsuario: Long,
//        idPublicacao: Long,
//        idEmpresa: Long,
//        dtCandidatura: LocalDate,
//    )

}