package cufa.conecta.com.resources.usuario

import cufa.conecta.com.application.dto.response.usuario.UsuarioTokenDto
import cufa.conecta.com.model.data.Usuario
import cufa.conecta.com.model.data.result.UsuarioResult

interface UsuarioRepository {
    fun cadastrarUsuario(usuario: Usuario)
    fun autenticar(data: Usuario): UsuarioTokenDto
    fun listarTodos(): List<UsuarioResult>
    fun mostrarDados(id:Long): List<Usuario>
    fun atualizar(usuario: Usuario)
    fun deletar(id: Long)

}