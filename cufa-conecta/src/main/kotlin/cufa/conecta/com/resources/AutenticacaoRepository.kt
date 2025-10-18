package cufa.conecta.com.resources

import cufa.conecta.com.application.exception.UsuarioAutenticadoNotFound
import cufa.conecta.com.resources.empresa.dao.EmpresaDao
import cufa.conecta.com.resources.empresa.dao.FuncionarioDao
import cufa.conecta.com.resources.usuario.dao.UsuarioDao
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AutenticacaoRepository(
    private val usuarioDao: UsuarioDao,
    private val empresaDao: EmpresaDao,
    private val funcionarioDao: FuncionarioDao
) : UserDetailsService {
    override fun loadUserByUsername(mail:String): UserDetails {
        val funcionario = funcionarioDao.findByEmail(mail).orElse(null)
        val usuario = usuarioDao.findByEmail(mail).orElse(null)
        val empresa = empresaDao.findByEmail(mail).orElse(null)

        return when {
            funcionario != null -> User(
                funcionario.email,
                funcionario.senha,
                emptyList()
            )

            usuario != null -> User(
                usuario.email,
                usuario.senha,
                emptyList()
            )

            empresa != null -> User(
                empresa.email,
                empresa.senha,
                emptyList()
            )
            else -> throw UsuarioAutenticadoNotFound("Usuário ou empresa com o email $mail não foi encontrado.")
        }
    }
}