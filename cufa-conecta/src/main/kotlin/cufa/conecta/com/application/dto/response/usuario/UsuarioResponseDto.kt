package cufa.conecta.com.application.dto.response.usuario

import java.time.LocalDate

data class UsuarioResponseDto(
    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String,
    val escolaridade: String,
    val dtNascimento: LocalDate,
    val estadoCivil: String,
    val estado: String,
    val cidade: String,
    val biografia: String,
    val curriculoUrl: String
)