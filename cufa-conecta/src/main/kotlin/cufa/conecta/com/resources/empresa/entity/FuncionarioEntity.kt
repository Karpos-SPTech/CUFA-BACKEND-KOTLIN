package cufa.conecta.com.resources.empresa.entity

import cufa.conecta.com.domain.enum.Cargo
import jakarta.persistence.*

@Entity(name = "funcionario")
data class FuncionarioEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    val idFuncionario: Long? = null,

    @Column(name = "fk_empresa")
    val fkEmpresa: Long,
    val nome: String,
    val email: String,
    val senha: String,
    @Enumerated(EnumType.STRING)
    val cargo: Cargo,
)