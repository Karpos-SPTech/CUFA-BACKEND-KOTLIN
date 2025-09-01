package cufa.conecta.com.application.dto.response.empresa

data class EmpresaTokenDto(
    val email: String,
    val nome: String,
    val tokenJwt: String?
)