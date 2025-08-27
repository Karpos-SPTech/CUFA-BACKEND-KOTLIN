package cufa.conecta.com.resources.empresa.dao

import cufa.conecta.com.resources.empresa.entity.FuncionarioEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import javax.swing.text.html.Option

@Repository
interface FuncionarioDao: JpaRepository<FuncionarioEntity, Long> {
    fun findByEmail(email: String): Optional<FuncionarioEntity>

    fun findByEmpresaId(empresaId: Long): List<FuncionarioEntity>
}