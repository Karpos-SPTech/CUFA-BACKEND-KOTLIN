package cufa.conecta.com.application.dto.request

data class LoginDto(
    val email: String,
    val senha: String
) {
    fun toModel(): LoginDto = LoginDto(email = email, senha = senha)
}