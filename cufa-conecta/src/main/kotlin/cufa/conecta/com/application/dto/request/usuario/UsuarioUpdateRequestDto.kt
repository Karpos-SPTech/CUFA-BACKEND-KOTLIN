package cufa.conecta.com.application.dto.request.usuario

import cufa.conecta.com.model.data.Usuario
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.br.CPF
import java.time.LocalDate

data class UsuarioUpdateRequestDto(
    @field:NotBlank(message = "O campo CPF não pode ser nulo, vazio ou branco")
    @field:Size(message = "O CPF deve conter 11 dígitos", min = 11, max = 11)
    @field:Digits(message = "O CPF deve conter apenas números", integer = 11, fraction = 0)
    @field:CPF(message = "CPF inválido")
    val cpf: String,
    @field:NotBlank(message = "O campo telefone não pode ser nulo, vazio ou branco")
    val telefone: String,
    @field:NotBlank(message = "O campo escolaridade não pode ser nulo, vazio ou branco")
    val escolaridade: String,
    @field:NotBlank(message = "O campo dtNascimento não pode ser nulo, vazio ou branco")
    val dtNascimento: LocalDate,
    @field:NotBlank(message = "O campo estado civil não pode ser nulo, vazio ou branco")
    val estadoCivil: String,
    @field:NotBlank(message = "O campo estado não pode ser nulo, vazio ou branco")
    val estado: String,
    @field:NotBlank(message = "O campo cidade não pode ser nulo, vazio ou branco")
    val cidade: String,
    @field:NotBlank(message = "O campo biografia não pode ser nulo, vazio ou branco")
    val biografia: String
) {
    fun toModel() = Usuario(
        cpf = cpf,
        telefone = telefone,
        escolaridade = escolaridade,
        dtNascimento = dtNascimento,
        estadoCivil = estadoCivil,
        estado = estado,
        cidade = cidade,
        biografia = biografia
    )


}