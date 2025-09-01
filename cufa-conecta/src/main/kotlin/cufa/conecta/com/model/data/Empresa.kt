package cufa.conecta.com.model.data

data class Empresa(
    val id: Long? = null,
    val nome: String,
    val email: String,
    val senha: String,
    val cep: String,
    val endereco: String,
    val numero: String,
    val cnpj: String,
    val area: String,
    val biografia: String? = null
)