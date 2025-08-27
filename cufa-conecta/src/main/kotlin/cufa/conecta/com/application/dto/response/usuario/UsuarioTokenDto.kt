package cufa.conecta.com.application.dto.response.usuario

data class UsuarioTokenDto(
    val id: Long,
    val nome: String,
    val email: String,
    val token: String
)