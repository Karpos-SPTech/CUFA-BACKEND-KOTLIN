package cufa.conecta.com.model.data

data class Candidato(
    private val nome: String,
    private val idade: Int,
    private val biografia: String,
    private val email: String,
    private val telefone: String,
    private val curriculoUrl: String,
    private val experiencias: List<Experiencia>
)