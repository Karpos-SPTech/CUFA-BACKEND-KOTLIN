package cufa.conecta.com.resources.empresa.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity(name = "cadastro_empresa")
data class EmpresaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    val idEmpresa: Long ?= null,
    val nome: String,
    val email: String,
    val senha: String,
    val cep: String,
    val endereco: String,
    val numero: Int,
    val cnpj: String,
    val area: String,
    val biografia: String ?= null,
    @Column(name = "dt_cadastro")
    val dtCadastro: LocalDate ?= null
)