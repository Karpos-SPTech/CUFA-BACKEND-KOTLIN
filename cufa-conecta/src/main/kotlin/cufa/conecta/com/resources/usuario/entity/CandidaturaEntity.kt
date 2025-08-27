package cufa.conecta.com.resources.usuario.entity

import cufa.conecta.com.resources.empresa.entity.EmpresaEntity
import cufa.conecta.com.resources.empresa.entity.PublicacaoEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity(name = "candidatura")
data class CandidaturaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario")
    val usuario: UsuarioEntity,

    @ManyToOne
    @JoinColumn(name = "fk_publicacao", referencedColumnName = "id_publicacao")
    val publicacao: PublicacaoEntity,

    @ManyToOne
    @JoinColumn(name = "fk_empresa", referencedColumnName = "id_empresa")
    val empresa: EmpresaEntity,

    @Column(name = "dt_candidatura")
    val candidatura: LocalDate,
)