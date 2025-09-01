package cufa.conecta.com.application.dto.response.empresa

import cufa.conecta.com.model.data.Funcionario

data class FuncionarioResponseDto(
    val nome: String,
    val email: String,
    val cargo: String
) {
    companion object {
        fun listOf(listaDeFuncionarios: List<Funcionario>): List<FuncionarioResponseDto> {
            return listaDeFuncionarios.map { data ->
                FuncionarioResponseDto(
                    nome = data.nome,
                    email = data.email,
                    cargo = data.cargo.toString()
                )
            }
        }
    }
}