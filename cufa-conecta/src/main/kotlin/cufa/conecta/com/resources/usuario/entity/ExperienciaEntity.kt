package cufa.conecta.com.resources.usuario.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity(name = "experiencias")
data class ExperienciaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_experiencia")
    val id: Long? = null,

    @Column(name = "id_usuario")
    var usuarioId: Long? = null,

    val cargo: String,

    val empresa: String,

    @Column(name = "dt_inicio")
    val dtInicio: LocalDate,

    @Column(name = "dt_fim")
    val dtFim: LocalDate
)
