package cufa.conecta.com.domain.service.usuario.implementation

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import cufa.conecta.com.application.dto.response.vagas.VagasRecomendadasResponseDto
import cufa.conecta.com.application.dto.response.usuario.UsuarioTokenDto
import cufa.conecta.com.application.dto.response.vagas.RecomendacaoDto
import cufa.conecta.com.application.dto.response.vagas.VagaProximaDto
import cufa.conecta.com.domain.service.ai.IaGenerativaService
import cufa.conecta.com.domain.service.usuario.UsuarioService
import cufa.conecta.com.model.data.Login
import cufa.conecta.com.model.data.usuario.Usuario
import cufa.conecta.com.model.data.result.UsuarioResult
import cufa.conecta.com.model.data.usuario.Localizacao
import cufa.conecta.com.resources.usuario.UsuarioRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UsuarioServiceImpl(
    private val repository: UsuarioRepository,
    private val iaService: IaGenerativaService,
    private val objectMapper: ObjectMapper
): UsuarioService {

    companion object {
        /** Deve refletir o raio (km) usado em `VagasDao.buscarVagasProximas`. */
        private const val RAIO_KM_RECOMENDACAO = 50
    }
    override fun cadastrarUsuario(data: Usuario) = repository.cadastrarUsuario(data)

    override fun autenticar(data: Login): UsuarioTokenDto = repository.autenticar(data)

    override fun mostrarDados(): UsuarioResult {
        val auth = SecurityContextHolder.getContext().authentication
        val email = auth?.name

        val dadosUsuario = repository.mostrarDados(email!!)

        return dadosUsuario
    }

    override fun atualizar(data: Usuario) {
        val auth = SecurityContextHolder.getContext().authentication

        val email = auth?.name

        repository.atualizar(data, email!!)
    }

    override fun atualizarCurriculoUrl(curriculoUrl: String?) {
        val auth = SecurityContextHolder.getContext().authentication
        val email = auth?.name

        repository.atualizarCurriculoUrl(email!!, curriculoUrl)
    }

    override fun atualizarLocalizacao(data: Localizacao) {
        val auth = SecurityContextHolder.getContext().authentication
        val email = auth?.name

        repository.atualizarLocalizacao(email!!, data)
    }

    override fun recomendarVagas(latitude: Double, longitude: Double): VagasRecomendadasResponseDto {
        val vagas = repository.buscarVagasProximas(latitude, longitude)

        // Resposta vazia (200): o app cai na listagem geral; 500 quebrava o fluxo e poluía o log.
        if (vagas.isEmpty()) {
            return VagasRecomendadasResponseDto(
                recomendacoes = emptyList(),
                totalVagas = 0,
                raioKm = RAIO_KM_RECOMENDACAO
            )
        }

        val resumoVagas = vagas.joinToString("\n") {
            "ID=${it.publicacaoId}; TITULO=${it.titulo};" +
            "TIPO_CONTRATO=${it.tipoContrato};" +
            "NOME_EMPRESA=${it.nomeEmpresa};" +
            "ID_EMPRESA=${it.empresaId}"
        }

        val prompt = """
            Você receberá uma lista de vagas já ordenadas por proximidade.
            
            Sua tarefa:
            - Retorne SOMENTE um JSON array de objetos.
            - Cada objeto deve ter: "id" (número), "titulo" (string), "tipoContrato" (string), "nomeEmpresa" (string), "empresaId" (string).
            - Mantenha a ordem da lista fornecida.
            - NÃO explique nada, NÃO adicione texto extra.
            
            Lista de vagas:
            $resumoVagas
        """

        val respostaString = iaService.gerarResposta(prompt)

        // A IA é usada só para reordenar: os dados (idEmpresa etc.) sempre vêm das vagas confiáveis,
        // evitando idEmpresa=0 quando a IA devolve o JSON em formato diferente do DTO.
        val ordemIa: List<Long>? = respostaString?.let { json ->
            runCatching {
                objectMapper.readValue(json, object : TypeReference<List<RecomendacaoIaItem>>() {})
                    .map { it.id }
            }.getOrNull()
        }

        val vagasPorId = vagas.associateBy { it.publicacaoId }
        val recomendacoes = ordemIa
            ?.mapNotNull { vagasPorId[it] }
            ?.takeIf { it.isNotEmpty() }
            ?.let { reordenadas ->
                val idsReordenados = reordenadas.map { it.publicacaoId }.toSet()
                val faltantes = vagas.filter { it.publicacaoId !in idsReordenados }
                mapearVagasSemIa(reordenadas + faltantes)
            }
            ?: mapearVagasSemIa(vagas)

        return VagasRecomendadasResponseDto(
            recomendacoes = recomendacoes,
            totalVagas = vagas.size,
            raioKm = RAIO_KM_RECOMENDACAO
        )
    }

    /** Só para ler a ordem sugerida pela IA; os demais campos são ignorados. */
    private data class RecomendacaoIaItem(val id: Long)

    private fun mapearVagasSemIa(vagas: List<VagaProximaDto>): List<RecomendacaoDto> =
        vagas.map {
            RecomendacaoDto(
                id = it.publicacaoId,
                nomeEmpresa = it.nomeEmpresa,
                idEmpresa = it.empresaId,
                titulo = it.titulo,
                tipoContrato = it.tipoContrato
            )
        }
}
