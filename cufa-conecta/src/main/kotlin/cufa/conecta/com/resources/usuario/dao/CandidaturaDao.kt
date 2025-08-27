package cufa.conecta.com.resources.usuario.dao

import cufa.conecta.com.resources.empresa.entity.PublicacaoEntity
import cufa.conecta.com.resources.usuario.entity.CandidaturaEntity
import cufa.conecta.com.resources.usuario.entity.UsuarioEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CandidaturaDao : JpaRepository<CandidaturaEntity, Long> {
    fun existsByUsuarioAndPublicacao(usuario: UsuarioEntity, vaga: PublicacaoEntity): Boolean

    fun findByUsuario(usuario: UsuarioEntity): List<CandidaturaEntity>

}