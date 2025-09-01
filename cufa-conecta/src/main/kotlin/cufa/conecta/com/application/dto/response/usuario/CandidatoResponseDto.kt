package cufa.conecta.com.application.dto.response.usuario

data class CandidatoResponseDto(
    val nome: String,
    val idade: Int,
    val biografia: String,
    val email: String,
    val telefone: String,
    val curriculoUrl: String,
    val experiencias: List<ExperienciaResponseDto>
)