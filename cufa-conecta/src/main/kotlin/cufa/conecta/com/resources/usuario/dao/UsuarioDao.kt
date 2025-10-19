package cufa.conecta.com.resources.usuario.dao

import cufa.conecta.com.resources.usuario.entity.UsuarioEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioDao : JpaRepository<UsuarioEntity, Long> {
    fun findByEmail(email: String?): Optional<UsuarioEntity>

    @Modifying
    @Query("UPDATE cadastro_usuario user SET user.curriculoUrl = :curriculoUrl WHERE user.id = :id")
    fun atualizarCurriculoUrl(id: Long, curriculoUrl: String?)

    @Query(
        """
            SELECT * FROM cadastro_usuario
            WHERE id_usuario IN (
              SELECT id_usuario from candidatura
              WHERE fk_publicacao = :publicacaoId
              )
            LIMIT :size
            OFFSET :offset
        """,
        nativeQuery = true
    )
    fun dadosPaginados(publicacaoId: Long, offset: Int, size: Int, usuarioId: Long): List<UsuarioEntity>
}
