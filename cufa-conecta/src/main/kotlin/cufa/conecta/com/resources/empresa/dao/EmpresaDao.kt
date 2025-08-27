package cufa.conecta.com.resources.empresa.dao

import cufa.conecta.com.resources.empresa.entity.EmpresaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface EmpresaDao: JpaRepository<EmpresaEntity, Long> {
    fun findByEmail(email: String?): Optional<EmpresaEntity>

    fun existsByEmail(email: String?): Boolean

    fun atualizarBiografia(biografia: String)
}