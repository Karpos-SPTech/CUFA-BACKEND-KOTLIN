package cufa.conecta.com.resources.empresa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(name = "publicacao")
data class PublicacaoEntity(
    @Id
    @GeneratedValue
    @Column(name = "id_publicacao")
    val idPublicacao: Long ?= null,

    @ManyToOne
    @JoinColumn(name= "fk_empresa", referencedColumnName = "id_empresa")
    val empresa: EmpresaEntity,
    val titulo: String,
    val descricao: String,
    @Column(name= "tipo_contrato")
    val tipoContrato: String,
    @Column(name= "dt_expiracao")
    val dtExpiracao: LocalDateTime,
    @Column(name= "dt_publicacao")
    val dtPublicacao: LocalDateTime,
)