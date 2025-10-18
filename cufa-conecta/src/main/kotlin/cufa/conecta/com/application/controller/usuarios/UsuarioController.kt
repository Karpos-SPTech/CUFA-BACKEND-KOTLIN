package cufa.conecta.com.application.controller.usuarios

import cufa.conecta.com.application.dto.request.LoginDto
import cufa.conecta.com.application.dto.request.usuario.UsuarioCadastroRequestDto
import cufa.conecta.com.application.dto.request.usuario.UsuarioUpdateRequestDto
import cufa.conecta.com.application.dto.response.usuario.UsuarioTokenDto
import cufa.conecta.com.application.exception.CreateInternalServerError
import cufa.conecta.com.domain.service.usuario.UsuarioService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController(
    private val service: UsuarioService
) {
    @PostMapping
    fun cadastrarUsuario(@RequestBody @Valid dto: UsuarioCadastroRequestDto) {
        val data = dto.toModel()

        runCatching {
            service.cadastrarUsuario(data)
        }.getOrElse {
            throw CreateInternalServerError("Falha ao cadastrar o usuário ${dto.nome}!!")
        }
        service.cadastrarUsuario(data)
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid dto: LoginDto): UsuarioTokenDto {
        val data = dto.toModel()
        val usuarioToken = service.autenticar(data)

        return UsuarioTokenDto(
            nome = usuarioToken.nome,
            email = usuarioToken.email,
            token = usuarioToken.token
        )
    }

    @PutMapping("/{id}")
    fun incrementarDadosDoUsuarios(
        @PathVariable id: Long,
        @RequestBody @Valid dto: UsuarioUpdateRequestDto
    ) {
        val usuarioAtualizado = dto.toModel()

        service.atualizar(usuarioAtualizado)
    }
}