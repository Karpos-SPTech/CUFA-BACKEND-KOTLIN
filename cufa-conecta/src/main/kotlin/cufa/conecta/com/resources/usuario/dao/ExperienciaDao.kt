package cufa.conecta.com.resources.usuario.dao

import cufa.conecta.com.resources.usuario.entity.ExperienciaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExperienciaDao : JpaRepository<ExperienciaEntity, Long> {
    fun findByUsuarioId(id: Long): List<ExperienciaEntity>
}