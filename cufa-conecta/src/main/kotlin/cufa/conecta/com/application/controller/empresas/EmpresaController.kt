package cufa.conecta.com.application.controller.empresas

import cufa.conecta.com.application.dto.request.LoginDto
import cufa.conecta.com.application.dto.request.empresa.BiografiaRequestDto
import cufa.conecta.com.application.dto.request.empresa.EmpresaRequestDto
import cufa.conecta.com.application.dto.response.empresa.EmpresaResponseDto
import cufa.conecta.com.application.dto.response.empresa.EmpresaTokenDto
import cufa.conecta.com.application.exception.CreateInternalServerError
import cufa.conecta.com.application.exception.EmpresaNotExistsException
import cufa.conecta.com.domain.service.empresa.EmpresaService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/empresas")
class EmpresaController(
    private val service: EmpresaService
) {
    @PostMapping
    @SecurityRequirement(name = "bearer")
    @ResponseStatus(HttpStatus.CREATED)
    fun cadastrar(@RequestBody @Valid dto: EmpresaRequestDto) {
        val empresaData = dto.toModel()

        runCatching {
            service.cadastrarEmpresa(empresaData)
        }.getOrElse {
            throw CreateInternalServerError("Falha ao cadastrar a empresa!!")
        }
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody loginDto: LoginDto): EmpresaTokenDto {
        val empresaData = loginDto.toModel()

        val empresaToken = service.autenticar(empresaData)

        return EmpresaTokenDto(
            nome = empresaToken.nome,
            email = empresaToken.email,
            tokenJwt = null
            //TODO validar o cookie
        )
    }

    @GetMapping
    @SecurityRequirement(name = "bearer")
    fun listarEmpresas(): List<EmpresaResponseDto> {
        val empresasEncontradas = service.listarTodos()

        if (empresasEncontradas.isEmpty()) { throw EmpresaNotExistsException("Não há nenhuma empresa cadastrada!") }

        val result = EmpresaResponseDto.listOf(empresasEncontradas)

        return result
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    fun buscarPorId(@PathVariable id: Long): EmpresaResponseDto {
        val empresaData = service.mostrarDados(id)

        val result = EmpresaResponseDto.of(empresaData)

        return result
    }

    @PutMapping
    @SecurityRequirement(name = "Bearer")
    fun atualizar(@RequestBody @Valid dto: EmpresaRequestDto) {
        val empresaAtualizada = dto.toModel()

        service.atualizarDados(empresaAtualizada)
    }

    @PatchMapping("/biografia")
    @SecurityRequirement(name = "Bearer")
    fun adicionarBiografia(@RequestBody @Valid dto: BiografiaRequestDto) =
       service.atualizarBiografia(dto.toString())

}