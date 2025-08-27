package cufa.conecta.com.model.data

data class Empresa(
    val id: Long?= null,
    val nome: String,
    val email: String,
    val senha: String,
    val cep: String,
    val numero: Int,
    val endereco: String,
    val cnpj: String,
    val area: String
)