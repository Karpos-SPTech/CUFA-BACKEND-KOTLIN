package cufa.conecta.com.application.controller.empresas

import cufa.conecta.com.application.dto.request.empresa.PublicacaoRequestDto
import cufa.conecta.com.application.dto.response.empresa.PublicacaoResponseDto
import cufa.conecta.com.domain.service.empresa.PublicacaoService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/publicacoes")
class PublicacaoController(
    private val service: PublicacaoService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criar(@RequestBody dto: PublicacaoRequestDto) {
        val data = dto.toModel()

        service.criar(data)
    }

    @GetMapping
    fun listarTodos(): List<PublicacaoResponseDto> {
        val publicacoesEncontradas = service.buscarTodas()

        val result = PublicacaoResponseDto.listOf(publicacoesEncontradas)

        return result
    }

    @GetMapping("/empresa")
    @SecurityRequirement(name = "Bearer")
    fun listarPorEmpresa(): List<PublicacaoResponseDto> {
        val publicacoesEncontradas = service.buscarPublicacoesDaEmpresa()

        val result = PublicacaoResponseDto.listOf(publicacoesEncontradas)

        return result
    }

    @PutMapping("/{id}")
    fun editar(@PathVariable id: Long, @RequestBody dto: PublicacaoRequestDto) {
        val data = dto.toModel()

        service.editarPublicacao(id, data)
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long) = service.delete(id)
}