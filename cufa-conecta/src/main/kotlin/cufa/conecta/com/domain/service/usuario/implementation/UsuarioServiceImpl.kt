package cufa.conecta.com.domain.service.usuario.implementation

import cufa.conecta.com.application.dto.request.LoginDto
import cufa.conecta.com.application.dto.response.usuario.UsuarioTokenDto
import cufa.conecta.com.domain.service.usuario.UsuarioService
import cufa.conecta.com.model.data.Usuario
import cufa.conecta.com.model.data.result.UsuarioResult
import cufa.conecta.com.resources.usuario.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class UsuarioServiceImpl(
    private val repository: UsuarioRepository
): UsuarioService {
    override fun cadastrarUsuario(data: Usuario) = repository.cadastrarUsuario(data)

    override fun autenticar(data: LoginDto): UsuarioTokenDto = repository.autenticar(data)

    override fun mostrarDados(id: Long): UsuarioResult = repository.mostrarDados(id)

    override fun atualizar(data: Usuario) = repository.atualizar(data)
}
