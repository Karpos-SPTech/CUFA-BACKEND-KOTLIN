package cufa.conecta.com.application.controller.empresas

import cufa.conecta.com.application.dto.request.empresa.FuncionarioRequestDto
import cufa.conecta.com.application.dto.response.empresa.FuncionarioResponseDto
import cufa.conecta.com.domain.service.empresa.FuncionarioService
import cufa.conecta.com.resources.empresa.exception.EmailAlreadyExistsException
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/funcionarios")
class FuncionarioController(
    private val service: FuncionarioService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criar(@RequestBody @Valid dto: FuncionarioRequestDto) {
        val funcionarioData = dto.toModel()

        service.criarFuncionario(funcionarioData)
    }

    @GetMapping("/{fkEmpresa}")
    @ResponseStatus(HttpStatus.OK)
    fun listarPorEmpresa(@PathVariable fkEmpresa: Long): List<FuncionarioResponseDto> {
        val funcionariosEncontrados = service.buscarPeloEmpresaId(fkEmpresa)

        if (funcionariosEncontrados.isEmpty()) throw EmailAlreadyExistsException("")

        val result = FuncionarioResponseDto.listOf(funcionariosEncontrados)

        return result
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun remover(@PathVariable id: Long) = service.deletar(id)
}