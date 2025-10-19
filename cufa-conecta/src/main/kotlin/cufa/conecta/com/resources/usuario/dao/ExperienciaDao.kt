package cufa.conecta.com.resources.usuario.dao

import cufa.conecta.com.resources.usuario.entity.ExperienciaEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ExperienciaDao : JpaRepository<ExperienciaEntity, Long> {
    fun findByUsuarioId(id: Long): List<ExperienciaEntity>

    @Transactional
    @Modifying
    @Query(
        value = """
            DELETE FROM experiencias e
            WHERE e.id = :idExperiencia
            AND e.usuarioId = :idUsuario
        """
    )
    fun deletarExperiencia(idExperiencia: Long, idUsuario: Long)

    @Modifying
    @Transactional
    @Query("""
    UPDATE experiencias e 
    SET 
        e.cargo = :titulo,
        e.empresa = :descricao,
        e.dtInicio = :tipoContrato,
        e.dtFim = :dtExpiracao
    WHERE 
        e.id = :idExperiencia
    AND 
        e.usuarioId = :idUsuario
""")
    fun atualizarExperiencia(
        idExperiencia: Long,
        idUsuario: Long,
        cargo: String,
        empresa: String,
        dtInicio: LocalDate,
        dtFim: LocalDate
    )
}