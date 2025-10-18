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

    fun existsByEmail(email: String?): Boolean

    @Modifying
    @Query("UPDATE cadastro_usuario user SET user.curriculoUrl = :curriculoUrl WHERE user.id = :id")
    fun atualizarCurriculoUrl(id: Long, curriculoUrl: String?)

    @Query(
        """ 
            SELECT candidatos from candidatura candidatos 
            WHERE candidatos.id = :id
            ORDER BY candidatos.usuario.id
            LIMIT :size
            OFFSET :offset 
        """
    )
    fun dadosPaginados(publicacaoId: Long, offset: Int, size: Int): List<UsuarioEntity>
}
