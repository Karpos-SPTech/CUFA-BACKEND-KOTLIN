package cufa.conecta.com.resources.empresa.dao

import cufa.conecta.com.resources.empresa.entity.PublicacaoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublicacaoDao : JpaRepository<PublicacaoEntity, Long> {

    fun findPublicacoesByEmpresaEmail(data: String): List<PublicacaoEntity>
}