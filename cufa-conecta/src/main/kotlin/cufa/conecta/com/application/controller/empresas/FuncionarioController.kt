package cufa.conecta.com.application.controller.empresas

import cufa.conecta.com.application.dto.request.empresa.FuncionarioRequestDto
import cufa.conecta.com.application.dto.response.empresa.FuncionarioResponseDto
import cufa.conecta.com.application.exception.CreateInternalServerError
import cufa.conecta.com.application.exception.FuncionarioNotExistsException
import cufa.conecta.com.domain.service.empresa.FuncionarioService
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

        runCatching {
            service.criarFuncionario(funcionarioData)
        }.getOrElse {
            throw CreateInternalServerError("Falha ao cadastrar o funcionário!!")
        }
    }

    @GetMapping("/{fkEmpresa}")
    @ResponseStatus(HttpStatus.OK)
    fun listarPorEmpresa(@PathVariable fkEmpresa: Long): List<FuncionarioResponseDto> {
        val funcionariosEncontrados = service.buscarPeloEmpresaId(fkEmpresa)

        if (funcionariosEncontrados.isEmpty())
            throw FuncionarioNotExistsException("Não há nenhum funcionario cadastrado!!")

        val result = FuncionarioResponseDto.listOf(funcionariosEncontrados)

        return result
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun remover(@PathVariable id: Long) = service.deletar(id)
}