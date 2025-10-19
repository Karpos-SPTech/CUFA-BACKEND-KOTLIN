package cufa.conecta.com.application.dto.request

import cufa.conecta.com.model.data.Login

data class LoginDto(
    val email: String,
    val senha: String
) {
    fun toModel() = Login(email = email, senha = senha)
}