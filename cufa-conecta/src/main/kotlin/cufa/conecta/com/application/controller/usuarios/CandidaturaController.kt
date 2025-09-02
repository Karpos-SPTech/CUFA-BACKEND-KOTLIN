package cufa.conecta.com.application.controller.usuarios

import cufa.conecta.com.application.dto.request.usuario.CandidaturaRequestDto
import cufa.conecta.com.application.dto.response.empresa.PublicacaoResponseDto
import cufa.conecta.com.application.dto.response.usuario.CandidaturaResponseDto
import cufa.conecta.com.domain.service.usuario.CandidaturaService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/candidaturas")
class CandidaturaController(
    private val service: CandidaturaService
) {
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @ResponseStatus(HttpStatus.CREATED)
    fun criarCandidatura(@RequestBody dto: CandidaturaRequestDto) {
        val data = dto.toModel()

        service.criarCandidatura(data)
    }

    @GetMapping("/{vagaId}")
    @ResponseStatus(HttpStatus.OK)
    fun listarInformacoes(@PathVariable vagaId: Long): CandidaturaResponseDto {
        val candidatura = service.listarDadosDaVaga(vagaId)

        val result = CandidaturaResponseDto.of(candidatura)

        return result
    }

    @GetMapping("/verificar/{userId}/{vagaId}")
    @SecurityRequirement(name = "Bearer")
    fun verificarCandidaturaExistente(@PathVariable userId: Long, @PathVariable vagaId: Long): Boolean {
        val candidaturaExistente = service.verificarCandidaturaExistente(userId, vagaId)

        return candidaturaExistente
    }

    @GetMapping("/usuario/{userId}")
    @SecurityRequirement(name = "Bearer")
    fun verCandidaturasPorUsuario(@PathVariable userId: Long): List<PublicacaoResponseDto> {
        val listaDeVagasCandidatas = service.listarPublicacoesCandidatadasPorUsuario(userId)

        val result = PublicacaoResponseDto.listOf(listaDeVagasCandidatas)

        return result
    }
}