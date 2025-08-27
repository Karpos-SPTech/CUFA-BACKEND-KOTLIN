package cufa.conecta.com.resources.empresa.entity

import cufa.conecta.com.domain.enum.Cargo
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "funcionario")
data class FuncionarioEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    val idFuncionario: Long? = null,

    @ManyToOne
    @JoinColumn(name = "fk_empresa", referencedColumnName = "id_empresa")
    val fkEmpresa: EmpresaEntity,

    val nome: String,
    val email: String,
    val senha: String,
    val cargo: Cargo,
)