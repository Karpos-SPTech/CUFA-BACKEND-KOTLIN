package cufa.conecta.com.resources.empresa.dao

import cufa.conecta.com.resources.empresa.entity.PublicacaoEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PublicacaoDao : JpaRepository<PublicacaoEntity, Long> {

    fun findByEmpresaId(empresaId: Long): List<PublicacaoEntity>

    @Query(
        value = """
            SELECT publicacoes 
            FROM publicacao publicacoes
            ORDER BY publicacoes.dtPublicacao
            LIMIT :size OFFSET :offset
        """
    )
    fun dadosPaginados(offset: Int, size: Int): List<PublicacaoEntity>


    @Transactional
    @Modifying
    @Query(
        value = """
            DELETE FROM publicacao p
            WHERE p.idPublicacao = :idPublicacao
            AND p.empresaId = :idEmpresa
        """
    )
    fun deletePublicacao(idPublicacao: Long, idEmpresa: Long)

    @Modifying
    @Transactional
    @Query("""
    UPDATE publicacao p 
    SET 
        p.titulo = :titulo,
        p.descricao = :descricao,
        p.tipoContrato = :tipoContrato,
        p.dtExpiracao = :dtExpiracao
    WHERE 
        p.idPublicacao = :idPublicacao
    AND 
        p.empresaId = :empresaId
""")
    fun atualizarPublicacao(
        idPublicacao: Long,
        empresaId: Long,
        titulo: String,
        descricao: String,
        tipoContrato: String,
        dtExpiracao: LocalDateTime
    )
}