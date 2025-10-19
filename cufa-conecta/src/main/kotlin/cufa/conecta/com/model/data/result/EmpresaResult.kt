package cufa.conecta.com.model.data.result

data class EmpresaResult (
    val nome: String,
    val email: String,
    val cep: String,
    val endereco: String,
    val numero: String,
    val cnpj: String,
    val area: String,
    val biografia: String ?= null
)