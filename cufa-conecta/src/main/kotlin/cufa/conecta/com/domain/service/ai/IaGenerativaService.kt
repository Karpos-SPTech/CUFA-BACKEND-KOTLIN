package cufa.conecta.com.domain.service.ai

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class IaGenerativaService(
    builder: WebClient.Builder,
    @Value("\${openai.api-key}") private val apiKey: String
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
}