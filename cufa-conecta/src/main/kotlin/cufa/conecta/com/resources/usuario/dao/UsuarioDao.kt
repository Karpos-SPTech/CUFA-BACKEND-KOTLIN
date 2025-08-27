package cufa.conecta.com.resources.usuario.dao

import cufa.conecta.com.resources.usuario.entity.UsuarioEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioDao : JpaRepository<UsuarioEntity, Long> {
    fun findByEmail(email: String?): UsuarioEntity

    fun existsByEmail(email: String?): Boolean

    fun findUsuariosByPublicacaoId(publicacaoId: Long): List<UsuarioEntity>
}