package cufa.conecta.com.application.dto.response.empresa

import cufa.conecta.com.model.data.result.EmpresaResult

data class EmpresaResponseDto(
    val nome: String,
    val email: String,
    val cep: String,
    val endereco: String,
    val numero: String,
    val cnpj: String,
    val area: String,
    val biografia: String
) {
    companion object {
        fun listOf(empresasResult: List<EmpresaResult>): List<EmpresaResponseDto> {
            return empresasResult.map { data ->
                EmpresaResponseDto(
                    nome = data.nome,
                    email = data.email,
                    cep = data.cep,
                    endereco = data.endereco,
                    numero = data.numero,
                    cnpj = data.cnpj,
                    area = data.area,
                    biografia = data.biografia
                )
            }
        }

        fun of(empresaResult: EmpresaResult) = EmpresaResponseDto(
            nome = empresaResult.nome,
            email = empresaResult.email,
            cep = empresaResult.cep,
            endereco = empresaResult.endereco,
            numero = empresaResult.numero,
            cnpj = empresaResult.cnpj,
            area = empresaResult.area,
            biografia = empresaResult.biografia
        )
    }
}
