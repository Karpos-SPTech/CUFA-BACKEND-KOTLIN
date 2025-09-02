package cufa.conecta.com.application.controller.usuarios

import cufa.conecta.com.application.dto.request.usuario.ExperienciaRequestDto
import cufa.conecta.com.application.dto.response.usuario.ExperienciaResponseDto
import cufa.conecta.com.domain.service.usuario.ExperienciaService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/experiencias")
class ExperienciaController(
    private val service: ExperienciaService
) {
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @ResponseStatus(HttpStatus.CREATED)
    fun criarExperiencia(@RequestBody dto: ExperienciaRequestDto) {
        val data = dto.toModel()

        service.criarExperiencia(data)
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    fun listarExperienciasPorUsuario(@PathVariable id: Long): List<ExperienciaResponseDto> {
        val listaDeExperiencias = service.listarPorUsuario(id)

        val result = ExperienciaResponseDto.listOf(listaDeExperiencias)

        return result
    }

    @PutMapping
    fun atualizarExperiencia(@RequestBody dto: ExperienciaRequestDto) {
        val empresaAtualizada = dto.toModel()

        service.atualizar(empresaAtualizada)
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletarExperiencia(@PathVariable id: Long) = service.deletarExperiencia(id)
}