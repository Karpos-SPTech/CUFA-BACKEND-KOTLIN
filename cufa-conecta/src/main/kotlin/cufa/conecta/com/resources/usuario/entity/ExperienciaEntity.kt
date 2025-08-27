package cufa.conecta.com.resources.usuario.entity

import cufa.conecta.com.application.dto.request.usuario.ExperienciaRequestDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
data class ExperienciaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "fk_usuario")
    var usuarioId: Long? = null,

    val cargo: String,

    val empresa: String,

    @Column(name = "dt_inicio")
    val dtInicio: LocalDate,

    @Column(name = "dt_fim")
    val dtFim: LocalDate
)
