package cufa.conecta.com.domain.service.ai

import com.fasterxml.jackson.databind.ObjectMapper
import cufa.conecta.com.application.dto.response.InsightDashboardResponseDto
import cufa.conecta.com.application.dto.response.ia.usuario.InsightDashboardDto
import cufa.conecta.com.model.data.empresa.Publicacao
import cufa.conecta.com.model.data.usuario.Experiencia
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import com.fasterxml.jackson.module.kotlin.readValue

@Service
class IaGenerativaService(
    builder: WebClient.Builder,
    @Value("\${openai.api-key}") private val apiKey: String,
    private val objectMapper: ObjectMapper
) {

    private val webClient = builder
        .baseUrl("https://api.openai.com/v1")
        .defaultHeader("Authorization", "Bearer $apiKey")
        .build()

    fun gerarResposta(prompt: String): String {

        val body = mapOf(
            "model" to "gpt-5.4-mini",
            "input" to prompt
        )

        val response = webClient.post()
            .uri("/responses")
            .bodyValue(body)
            .retrieve()
            .bodyToMono(Map::class.java)
            .block() ?: throw RuntimeException("Resposta nula da OpenAI")

        return extrairTexto(response)
    }

    fun gerarInsightsEmpregabilidade(
        experiencias: List<Experiencia>,
        vagas: List<Publicacao>
    ): List<InsightDashboardDto> {

        val prompt = construirPromptEmpregabilidade(experiencias, vagas)

        val respostaIa = gerarResposta(prompt)

        return runCatching {
            val response: InsightDashboardResponseDto =
                objectMapper.readValue(respostaIa)

            response.insights

        }.getOrElse {
            throw RuntimeException("Erro ao deserializar JSON da OpenAI: ${it.message}")
        }
    }

    private fun extrairTexto(response: Map<*, *>?): String {
        val output = response?.get("output") as? List<*> ?: return ""

        val textos = output.mapNotNull { item ->
            val map = item as? Map<*, *>
            val content = map?.get("content") as? List<*>

            content?.mapNotNull { c ->
                val cMap = c as? Map<*, *>
                cMap?.get("text") as? String
            }
        }.flatten()

        return textos.joinToString("\n").ifBlank {
            throw RuntimeException("Resposta vazia da OpenAI")
        }
    }

    private fun construirPromptEmpregabilidade(
        experiencias: List<Experiencia>,
        vagas: List<Publicacao>
    ): String {

        return """
            Você é um especialista em recrutamento.
        
            Analise o perfil do candidato:
        
            Experiências:
            ${
                experiencias.joinToString("\n") { "- ${it.cargo}" }
            }
        
            Vagas disponíveis:
            ${
                vagas.take(20).joinToString("\n") {
                    "- ${it.titulo}: ${it.descricao}"
                }
            }
        
            Analise as experiências do usuário e compare com as vagas disponíveis.
        
            Gere entre 1 e 5 observações relevantes.
        
            Somente gere observações quando houver alguma conclusão útil.
        
            Não invente informações.
            Não repita a mesma ideia com palavras diferentes.
            Se houver poucos dados, retorne menos observações.
        
            Retorne APENAS JSON válido.
        
            Não utilize markdown.
            Não utilize ```json.
            Não escreva nenhum texto fora do JSON.
        
            Formato obrigatório:
        
            {
              "insights": [
                {
                  "texto": "Sua observação aqui"
                }
              ]
            }
        """.trimIndent()
    }
}